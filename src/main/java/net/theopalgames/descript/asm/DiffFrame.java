package net.theopalgames.descript.asm;

import org.objectweb.asm.Opcodes;

public final class DiffFrame extends Frame {
	public static final int F_MOD_STACK = -2;
	public static final int F_INSERT_STACK = -3;
	public static final int F_REPLACE_TOP = -4;
	
	public final int type;
	
	public DiffFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		super(nLocal, local, nStack, stack);
		this.type = type;
	}
	
	public static final DiffFrame SAME = new DiffFrame(Opcodes.F_SAME, 0, new Object[0], 0, new Object[0]);
	
	public static DiffFrame SAME_1(Object stack) {
		return new DiffFrame(Opcodes.F_SAME1, 0, new Object[0], 1, new Object[] {stack});
	}
	
	public static DiffFrame APPEND(Object... newLocals) {
		return new DiffFrame(Opcodes.F_APPEND, newLocals.length, newLocals, 0, new Object[0]);
	}
	
	public static DiffFrame CHOP(int toRemove) {
		return new DiffFrame(Opcodes.F_CHOP, -toRemove, new Object[0], 0, new Object[0]);
	}
	
	public static DiffFrame FULL(Frame frame) {
		return new DiffFrame(Opcodes.F_FULL, frame.nLocal, frame.local, frame.nStack, frame.stack);
	}
	
	public static DiffFrame APPEND_STACK(int nStack, Object... stack) {
		return new DiffFrame(F_MOD_STACK, 0, new Object[0], nStack, stack);
	}
	
	public static DiffFrame INSERT_STACK(int pos, Object... entries) {
		return new DiffFrame(F_INSERT_STACK, 0, new Object[0], entries.length, new Object[] {pos, entries});
	}
	
	public static DiffFrame REPLACE_TOP(Object newTop) {
		return new DiffFrame(F_REPLACE_TOP, 0, new Object[0], 1, new Object[] {newTop});
	}
}
