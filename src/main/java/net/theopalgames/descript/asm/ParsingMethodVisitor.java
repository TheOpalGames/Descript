package net.theopalgames.descript.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.TypePath;

public final class ParsingMethodVisitor extends MethodVisitor {
	private final int flags;
	
	private boolean visitedInitialFrame = false;
	private Frame frame;
	
	public ParsingMethodVisitor(int api, int flags, MethodVisitor mv, String desc) {
		super(api, mv);
		this.flags = flags;
		
		if ((flags & ClassReader.EXPAND_FRAMES) != 0) {
			Type type = Type.getMethodType(desc);
			frame = new Frame(type.getArgumentTypes().length, toFrame(type.getArgumentTypes()), 0, new Object[0]);
		}
	}
	
	static Object[] toFrame(Type[] types) {
		Object[] ret = new Object[types.length+1];
		ret[0] = Opcodes.UNINITIALIZED_THIS;
		
		for (int i = 0; i < types.length; i++)
			ret[i+1] = toFrameType(types[i]);
		
		return ret;
	}
	
	static Object toFrameType(Type type) {
		String desc = type.getDescriptor();
		
		if (desc.startsWith("L"))
			return desc.substring(1, desc.length()-1);
		
		if (desc.startsWith("["))
			return desc;
		
		switch (desc) {
		case "Z":
		case "B":
		case "I":
			return Opcodes.INTEGER;
		case "F":
			return Opcodes.FLOAT;
		case "D":
			return Opcodes.DOUBLE;
		case "J":
			return Opcodes.LONG;
		default:
			throw new IllegalArgumentException("Bad descriptor: " + desc);
		}
	}
	
	@Override
	public void visitParameter(String name, int access) {
		if ((flags & ClassReader.SKIP_DEBUG) == 0)
			mv.visitParameter(name, access);
	}
	
	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		if ((flags & ClassReader.SKIP_FRAMES) != 0)
			return;
		
		if ((flags & ClassReader.EXPAND_FRAMES) == 0) {
			mv.visitFrame(type, nLocal, local, nStack, stack);
			return;
		}
		
		DiffFrame diff = new DiffFrame(type, nLocal, local, nStack, stack);
		frame = frame.apply(diff);
		mv.visitFrame(Opcodes.F_NEW, frame.nLocal, frame.local, frame.nStack, frame.stack);
	}
	
	@Override
	public void visitLineNumber(int line, Label start) {
		if ((flags & ClassReader.SKIP_DEBUG) == 0)
			mv.visitLineNumber(line, start);
	}
	
	@Override
	public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
		if ((flags & ClassReader.SKIP_DEBUG) == 0)
			mv.visitLocalVariable(name, descriptor, signature, start, end, index);
	}
	
	@Override
	public void visitCode() {
		if (skipCode())
			mv.visitCode();
	}
	
	@Override
	public void visitInsn(int opcode) {
		if (skipCode())
			mv.visitInsn(opcode);
	}
	
	@Override
	public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
		if (skipCode())
			return mv.visitInsnAnnotation(typeRef, typePath, descriptor, visible);
		
		return null;
	}
	
	@Override
	public void visitLdcInsn(Object value) {
		if (skipCode())
			mv.visitLdcInsn(value);
	}
	
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
		if (skipCode())
			mv.visitFieldInsn(opcode, owner, name, descriptor);
	}
	
	@Override
	public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
		if (skipCode())
			mv.visitMultiANewArrayInsn(descriptor, numDimensions);
	}
	
	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
		if (skipCode())
			mv.visitTableSwitchInsn(min, max, dflt, labels);
	}
	
	@Override
	public void visitIincInsn(int var, int increment) {
		if (skipCode())
			mv.visitIincInsn(var, increment);
	}
	
	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		if (skipCode())
			mv.visitLookupSwitchInsn(dflt, keys, labels);
	}
	
	@Override
	public void visitJumpInsn(int opcode, Label label) {
		if (skipCode())
			mv.visitJumpInsn(opcode, label);
	}
	
	@Override
	public void visitIntInsn(int opcode, int operand) {
		if (skipCode())
			mv.visitIntInsn(opcode, operand);
	}
	
	@Override
	public void visitTypeInsn(int opcode, String type) {
		if (skipCode())
			mv.visitTypeInsn(opcode, type);
	}
	
	@Override
	public void visitVarInsn(int opcode, int var) {
		if (skipCode())
			mv.visitVarInsn(opcode, var);
	}
	
	@Override
	public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
		if (skipCode())
			mv.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
		if (skipCode())
			mv.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
	}
	
	private boolean skipCode() {
		if (!visitedInitialFrame) {
			if ((flags & ClassReader.EXPAND_FRAMES) != 0)
				visitFrame(Opcodes.F_NEW, frame.nLocal, frame.local, frame.nStack, frame.stack);
			
			visitedInitialFrame = true;
		}
		
		return (flags & ClassReader.SKIP_CODE) == 0;
	}
}
