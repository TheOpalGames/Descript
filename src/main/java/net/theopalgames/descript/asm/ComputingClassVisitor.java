package net.theopalgames.descript.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class ComputingClassVisitor extends ClassVisitor {
	private int version;
	
	public ComputingClassVisitor(int flags, ClassVisitor cv) {
		super(Opcodes.ASM6, findDelegate(flags, cv));
	}
	
	private static ClassVisitor findDelegate(int flags, ClassVisitor cv) {
		if ((flags & ClassWriter.COMPUTE_FRAMES) != 0)
			return new FrameComputingClassVisitor(cv);
		
		return cv;
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
		
		return new ComputingMethodVisitor(api, version, desc, mv);
	}
}
