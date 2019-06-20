package net.theopalgames.descript.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class ComputingClassVisitor extends ClassVisitor {
	private final boolean computeFrames;
	private int version;
	
	public ComputingClassVisitor(int flags, ClassVisitor cv) {
		super(Opcodes.ASM6, cv);
		this.computeFrames = ((flags & ClassWriter.COMPUTE_FRAMES) != 0);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		this.version = version;
		cv.visit(version, access, name, signature, superName, interfaces);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (mv == null)
			return null;
		
		return new ComputingMethodVisitor(api, computeFrames, version, desc, mv);
	}
}
