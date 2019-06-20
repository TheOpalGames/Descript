package net.theopalgames.descript.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public final class ParsingClassVisitor extends ClassVisitor {
	private final int flags;
	
	public ParsingClassVisitor(int flags, ClassVisitor cv) {
		super(Opcodes.ASM6, cv);
		this.flags = flags;
	}
	
	@Override
	public void visitSource(String source, String debug) {
		if ((flags & ClassReader.SKIP_DEBUG) == 0)
			cv.visitSource(source, debug);
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (mv == null)
			return null;
		
		return new ParsingMethodVisitor(api, flags, mv, desc);
	}
}
