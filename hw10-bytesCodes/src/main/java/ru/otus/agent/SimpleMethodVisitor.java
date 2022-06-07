package ru.otus.agent;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class SimpleMethodVisitor extends MethodVisitor {

    private boolean isLogEnabled = false;
    private final String methodName;
    private final Type[] argTypes;

    public SimpleMethodVisitor(int api, MethodVisitor methodVisitor, String methodName, String descriptor) {
        super(api, methodVisitor);
        this.methodName = methodName;

        Type methodType = Type.getMethodType(descriptor);
        argTypes = methodType.getArgumentTypes();

    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals("Lproxy/Log;")) {
            isLogEnabled = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }


    @Override
    public void visitCode() {
        super.visitCode();
        if (isLogEnabled) { // надо залогировать
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("executed method: " + methodName + ", param: ");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);

            for (int i = 0; i < argTypes.length; ++i) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitVarInsn(getOpcode(argTypes[i].getClassName()), i + 1);
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(" + argTypes[i].getDescriptor() + ")V", false);
            }

        }
    }

    private int getOpcode(String type) {
        switch (type) {
            case "int":
                return Opcodes.ILOAD;
            case "java.lang.String":
            default:
                return Opcodes.ALOAD;
        }
    }
}
