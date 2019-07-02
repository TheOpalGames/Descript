package net.theopalgames.descript.asm;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Frame {
	public final int nLocal;
	public final Object[] local;
	public final int nStack;
	public final Object[] stack;
	
	public DiffFrame diff(Frame other) {
		if (nLocal == other.nLocal && nStack == other.nStack) {
			if (Arrays.equals(stack, other.stack) && Arrays.equals(local, other.local))
				return DiffFrame.SAME;
			
			return DiffFrame.FULL(other);
		}
		
		if (Arrays.equals(local, other.local) && other.nStack == 1)
			return DiffFrame.SAME_1(other.stack[0]);
		
		if (Arrays.equals(stack, other.stack) && 1 <= other.nLocal-nLocal && other.nLocal-nLocal <= 3) {
			Object[] newLocals = new Object[other.nLocal-nLocal];
			System.arraycopy(other.local, nLocal, newLocals, 0, newLocals.length);
			return DiffFrame.APPEND(newLocals);
		}
		
		if (Arrays.equals(stack, other.stack) && 1 <= nLocal-other.nLocal && nLocal-other.nLocal <= 3)
			return DiffFrame.CHOP(nLocal-other.nLocal);
		
		return DiffFrame.FULL(other);
	}
}
