package org.arnoldc.ast

import org.objectweb.asm.MethodVisitor
import org.arnoldc.{SymbolTable}
import org.objectweb.asm.Opcodes._
import org.parboiled.errors.ParsingException


case class DeclareIntNode(variable: String, value: OperandNode) extends StatementNode {

  def generate(mv: MethodVisitor, symbolTable: SymbolTable) = {
    symbolTable.putVariable(variable)
    value match {
      case _: StringNode => throw new ParsingException("CANNOT INITIALIZE INT WITH STRING VALUE")
      case _: FloatNode => throw new ParsingException("CANNOT INITIALIZE INT WITH FLOAT VALUE")
      case _ =>
        value.generate(mv, symbolTable)
        mv.visitVarInsn(ISTORE, symbolTable.getVariableAddress(variable))
    }
  }

}