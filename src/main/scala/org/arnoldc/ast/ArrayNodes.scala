package org.arnoldc.ast

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes._
import org.arnoldc.SymbolTable
import org.arnoldc.VariableType

// I AIN'T GOT TIME TO BLEED <name> WITH <size> UGLY MOTHERFUCKERS
case class ArrayDeclareNode(variableName: String, size: OperandNode) extends StatementNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    symbolTable.putVariable(variableName, VariableType.IntArrayType)
    size.generate(mv, symbolTable)
    mv.visitIntInsn(NEWARRAY, T_INT)
    mv.visitVarInsn(ASTORE, symbolTable.getVariableAddress(variableName))
  }
}

// GET IN LINE <name> AT <index>  (read an element)
case class ArrayAccessNode(variableName: String, index: OperandNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    mv.visitVarInsn(ALOAD, symbolTable.getVariableAddress(variableName))
    index.generate(mv, symbolTable)
    mv.visitInsn(IALOAD)
  }
}

// HOW MANY OF THEM <name>  (array length)
case class ArrayLengthNode(variableName: String) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    mv.visitVarInsn(ALOAD, symbolTable.getVariableAddress(variableName))
    mv.visitInsn(ARRAYLENGTH)
  }
}

// GET IN LINE <name> AT <index> / HERE IS MY INVITATION <expr> ... / ENOUGH TALK
case class ArrayAssignNode(variableName: String, index: OperandNode, expression: AstNode) extends StatementNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    mv.visitVarInsn(ALOAD, symbolTable.getVariableAddress(variableName))
    index.generate(mv, symbolTable)
    expression.generate(mv, symbolTable)
    mv.visitInsn(IASTORE)
  }
}
