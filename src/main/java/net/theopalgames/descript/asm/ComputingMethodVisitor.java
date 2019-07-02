package net.theopalgames.descript.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public final class ComputingMethodVisitor extends MethodVisitor {
	private final boolean computeFrames;
	private final int version;
	
	private int maxStack = 0;
	private int maxLocals = 0;
	
	private Frame frame;
	
	public ComputingMethodVisitor(int api, boolean computeFrames, int version, String desc, MethodVisitor mv) {
		super(api, mv);
		this.computeFrames = computeFrames;
		this.version = version;
		
		Type type = Type.getMethodType(desc);
		Object[] local = ParsingMethodVisitor.toFrame(type.getArgumentTypes());
		frame = new Frame(local.length, local, 0, new Object[0]);
	}
	
	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitMaxs(this.maxStack, this.maxLocals);
	}
	
	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		if (local[0].equals(Opcodes.UNINITIALIZED_THIS)) {
			if (version < Opcodes.V1_6)
				mv.visitFrame(type, nLocal, local, nStack, stack);
			
			return;
		}
		
		if (!computeFrames && (type == Opcodes.F_NEW && version < Opcodes.V1_6)) {
			mv.visitFrame(type, nLocal, local, nStack, stack);
			return;
		}
		
		if (computeFrames) {
			computeFrame();
			return;
		}
		
		if (type == Opcodes.F_NEW) {
			compressFrame(nLocal, local, nStack, stack);
			return;
		}
	}
	
	private void computeFrame() {
		// TODO
	}
	
	private void compressFrame(int nLocal, Object[] local, int nStack, Object[] stack) {
		Frame newFrame = new Frame(nLocal, local, nStack, stack);
		DiffFrame diff = frame.diff(newFrame);
		frame = newFrame;
		
		mv.visitFrame(diff.type, diff.nLocal, diff.local, diff.nStack, diff.stack);
	}
}
