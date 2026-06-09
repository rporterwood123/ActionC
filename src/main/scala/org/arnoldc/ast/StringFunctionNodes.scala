package org.arnoldc.ast

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes._
import org.arnoldc.SymbolTable

// Standard-library string functions, compiled to java/lang/String calls.

// --- String-returning (usable wherever a string operand is expected) ---

// SAY IT LOUDER <str>  -> str.toUpperCase()
case class UpperNode(str: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toUpperCase", "()Ljava/lang/String;")
  }
}

// KEEP YOUR VOICE DOWN <str>  -> str.toLowerCase()
case class LowerNode(str: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;")
  }
}

// CUT THE FAT FROM <str>  -> str.trim()
case class TrimNode(str: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "trim", "()Ljava/lang/String;")
  }
}

// SPELL IT OUT <n>  -> String.valueOf(n). Type-aware: floats use the float overload
// so they stringify as e.g. "3.5"; everything else uses the int overload.
case class NumToStringNode(arg: OperandNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    arg.generate(mv, symbolTable)
    if (TypeInference.isFloat(arg, symbolTable)) {
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(F)Ljava/lang/String;")
    } else {
      mv.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(I)Ljava/lang/String;")
    }
  }
}

// GIVE ME A PIECE OF <str> FROM <begin> TO <end>  -> str.substring(begin, end)
case class SubstringNode(str: AstNode, begin: OperandNode, end: OperandNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    begin.generate(mv, symbolTable)
    end.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "substring", "(II)Ljava/lang/String;")
  }
}

// --- Int-returning (usable wherever an integer operand is expected) ---

// HOW LONG IS THIS THING <str>  -> str.length()
case class LengthNode(str: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I")
  }
}

// YOU TALKING TO ME ABOUT <str> <sub>  -> str.contains(sub) ? 1 : 0
case class ContainsNode(str: AstNode, sub: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    sub.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z")
  }
}

// DO THE MATH <str>  -> Integer.parseInt(str)
case class ParseIntNode(str: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    str.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "parseInt", "(Ljava/lang/String;)I")
  }
}

// WHERE IS IT IN <haystack> <needle>  -> haystack.indexOf(needle)
case class IndexOfNode(haystack: AstNode, needle: AstNode) extends OperandNode {
  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
    haystack.generate(mv, symbolTable)
    needle.generate(mv, symbolTable)
    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "indexOf", "(Ljava/lang/String;)I")
  }
}
