package org.arnoldc.ast

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes._
import org.arnoldc.SymbolTable

case class VariableNode(variableName: String) extends OperandNode{
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    val variableType = symbolTable.getVariableType(variableName)
    mv.visitVarInsn(variableType.loadOpcode, symbolTable.getVariableAddress(variableName))
  }
}
