package ru.otus.agent;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class SimpleClassVisitor extends ClassVisitor{

    private final int version;

    public SimpleClassVisitor(int version, ClassWriter classWriter) {
        super(version, classWriter);
        this.version = version;
    }

    @Override
    public MethodVisitor visitMethod(int access,
                                     String name,
                                     String descriptor,
                                     String signature,
                                     String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        return new SimpleMethodVisitor(version, mv, name, descriptor);
    }
}
