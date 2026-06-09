package org.arnoldc

import scala.collection.mutable
import org.parboiled.errors.ParsingException
import org.objectweb.asm.Opcodes._
import org.objectweb.asm.Label

case class SymbolTable(upperLevel: Option[SymbolTable], currentMethod: String) {

  val FirstSymbolTableAddress = 0
  private val variableTable = new mutable.HashMap[String, Integer]()
  private val typeTable = new mutable.HashMap[String, VariableType]()
  private val methodTable = new mutable.HashMap[String, MethodInformation]()

  // Stack of (continueLabel, breakLabel) for the enclosing loops, innermost last.
  // Used by GET OUT (break) and KEEP MOVING (continue).
  private var loopContexts: List[(Label, Label)] = Nil

  def enterLoop(continueLabel: Label, breakLabel: Label): Unit = {
    loopContexts = (continueLabel, breakLabel) :: loopContexts
  }

  def exitLoop(): Unit = {
    loopContexts = loopContexts.tail
  }

  def currentBreakLabel: Label = loopContexts match {
    case (_, breakLabel) :: _ => breakLabel
    case Nil => throw new ParsingException("GET OUT USED OUTSIDE OF A LOOP")
  }

  def currentContinueLabel: Label = loopContexts match {
    case (continueLabel, _) :: _ => continueLabel
    case Nil => throw new ParsingException("KEEP MOVING USED OUTSIDE OF A LOOP")
  }

  val initialNextVarAddress: Int = FirstSymbolTableAddress

  def size(): Int = {
    initialNextVarAddress + variableTable.size
  }

  def putVariable(variableName: String): Unit = putVariable(variableName, VariableType.IntType)

  def putVariable(variableName: String, variableType: VariableType): Unit = {
    val newVarAddress = initialNextVarAddress + variableTable.size
    if (variableTable.contains(variableName)) {
      throw new ParsingException("DUPLICATE VARIABLE: " + variableName)
    }
    variableTable += (variableName -> newVarAddress)
    typeTable += (variableName -> variableType)
  }

  def getVariableType(variableName: String): VariableType = {
    typeTable.getOrElse(variableName, {
      if (upperLevel.isEmpty) {
        throw new ParsingException("VARIABLE: " + variableName + " NOT DECLARED!")
      }
      upperLevel.get.getVariableType(variableName)
    })
  }

  def containsVariable(variableName: String): Boolean = {
    variableTable.contains(variableName) || upperLevel.exists(_.containsVariable(variableName))
  }

  def getVariableAddress(variableName: String): Integer = {
    variableTable.getOrElse(variableName, {
      if (upperLevel.isEmpty) {
        throw new ParsingException("VARIABLE: " + variableName + " NOT DECLARED!")
      }
      upperLevel.get.getVariableAddress(variableName)
    })
  }

  def putMethod(methodName: String, methodInformation: MethodInformation) = {
    methodTable.put(methodName, methodInformation)
  }

  def getMethodDescription(methodName: String): String = {
    if (methodName.equals("main")) {
      "([Ljava/lang/String;)V"
    }
    else {
      val method = getMethodInformation(methodName)
      val numberOfArguments = method.numberOfArguments
      val returnValue = if (method.returnsValue) "I" else "V"
      "(" + "I" * numberOfArguments + ")" + returnValue
    }
  }

  def getCurrentMethod(): MethodInformation = {
    getMethodInformation(currentMethod)
  }

  def getMethodInformation(methodName: String): MethodInformation = {
    methodTable.getOrElse(methodName, {
      if (upperLevel.isEmpty) {
        throw new ParsingException("METHOD: " + methodName + " NOT DECLARED!")
      }
      upperLevel.get.getMethodInformation(methodName)
    })
  }

  def getFileName(): String = {
    if (upperLevel.isEmpty) {
      currentMethod
    }
    else {
      upperLevel.get.getFileName()
    }
  }

}