package org.arnoldc.ast

import org.objectweb.asm.Opcodes._
import org.objectweb.asm.{MethodVisitor, ClassWriter}
import org.arnoldc.{MethodInformation, SymbolTable}

case class RootNode(classes: List[ClassDefNode], methods: List[AbstractMethodNode]) extends AstNode {

  def generateByteCode(filename: String): Map[String, Array[Byte]] = {
    val globalSymbols = storeMethodSignatures(filename)
    // Register class metadata before generating any bodies, so field/method
    // resolution (including inheritance) works during code generation.
    classes.foreach(c => globalSymbols.registerClass(c.metadata))

    val mainClass = Map(filename -> generateClass(filename, globalSymbols).toByteArray)
    val classFiles = classes.map(c => c.className -> c.generateClass(globalSymbols)).toMap
    mainClass ++ classFiles
  }

  def generate(mv: MethodVisitor, symbolTable: SymbolTable) {
  }


  def storeMethodSignatures(filename: String) = {
    def storeTo(symbols: SymbolTable)(s: MethodSignature) = {
      symbols.putMethod(s.name, new MethodInformation(s.returnsValue, s.args.size))
    }
    val globalSymbols = new SymbolTable(None, filename)
    val methodSignatures = methods.map(_.signature)
    methodSignatures.foreach(storeTo(globalSymbols))
    globalSymbols
  }

  def generateClass(className: String, globalSymbols: SymbolTable): ClassWriter = {
    val cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
    def generateClassHeader() = {
      cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null)
      cw.visitSource("Hello.java", null)
      val mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null)
      mv.visitVarInsn(ALOAD, 0)
      mv.visitMethodInsn(INVOKESPECIAL,
        "java/lang/Object",
        "<init>",
        "()V")
      mv.visitInsn(RETURN)
      mv.visitMaxs(100, 100)
      mv.visitEnd()
      mv
    }
    def generateClassBody(methodVisitor: MethodVisitor) = {
      def generateBytecode(method: AbstractMethodNode) {
        method.generate(cw.visitMethod(ACC_PUBLIC + ACC_STATIC,
          method.methodName,
          globalSymbols.getMethodDescription(method.methodName), null, null),
          globalSymbols)
      }
      methods.foreach(generateBytecode)
    }
    val methodVisitor = generateClassHeader()
    generateClassBody(methodVisitor)
    cw
  }


}
