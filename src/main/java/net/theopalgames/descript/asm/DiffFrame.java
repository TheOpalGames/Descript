package net.theopalgames.descript.asm;

import org.objectweb.asm.Opcodes;

public final class DiffFrame extends Frame {
	public static final DiffFrame SAME = new DiffFrame(Opcodes.F_SAME, 0, new Object[0], 0, new Object[0]);
	
	public final int type;
	
	private DiffFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		super(nLocal, local, nStack, stack);
		this.type = type;
	}
	
	public static DiffFrame SAME_1(Object stack) {
		return new DiffFrame(Opcodes.F_SAME1, 0, new Object[0], 1, new Object[] {stack});
	}
	
	public static DiffFrame APPEND(Object[] newLocals) {
		return new DiffFrame(Opcodes.F_APPEND, newLocals.length, newLocals, 0, new Object[0]);
	}
	
	public static DiffFrame CHOP(int toRemove) {
		return new DiffFrame(Opcodes.F_CHOP, -toRemove, new Object[0], 0, new Object[0]);
	}
	
	public static DiffFrame FULL(Frame frame) {
		return new DiffFrame(Opcodes.F_FULL, frame.nLocal, frame.local, frame.nStack, frame.stack);
	}
}
