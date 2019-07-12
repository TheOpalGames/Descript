package net.theopalgames.descript.asm;

import java.util.Arrays;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FrameNode;

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
	
	public Frame apply(DiffFrame diff) {
		switch (diff.type) {
		case Opcodes.F_NEW:
		case Opcodes.F_FULL:
			return new Frame(nLocal, local, nStack, stack);
		case Opcodes.F_APPEND:
			Object[] bigLocal = new Object[nLocal+diff.nLocal];
			System.arraycopy(local, 0, bigLocal, 0, nLocal);
			System.arraycopy(diff.local, 0, bigLocal, local.length, nLocal);
			return new Frame(nLocal+diff.nLocal, bigLocal, nStack, stack);
		case Opcodes.F_CHOP:
			Object[] smallLocal = new Object[local.length+diff.nLocal];
			System.arraycopy(local, 0, smallLocal, 0, nLocal+diff.nLocal);
			return new Frame(nLocal+diff.nLocal, smallLocal, nStack, stack);
		case Opcodes.F_SAME:
			return new Frame(nLocal, local, 0, new Object[0]);
		case Opcodes.F_SAME1:
			return new Frame(nLocal, local, 1, stack);
		case DiffFrame.F_MOD_STACK:
			Object[] modifiedStack = new Object[nStack+diff.nStack];
			System.arraycopy(stack, 0, modifiedStack, 0, Math.min(stack.length, modifiedStack.length));
			
			if (diff.nStack > 0)
				System.arraycopy(diff.stack, nStack, modifiedStack, 0, diff.nStack);
			
			return new Frame(nLocal, local, modifiedStack.length, modifiedStack);
		}
		
		throw new IllegalArgumentException("Unknown frame type: " + diff.type);
	}
	
	public FrameNode toNode() {
		return new FrameNode(Opcodes.F_NEW, nLocal, local, nStack, stack);
	}
	
	public Object lastStack() {
		return stack[stack.length-1];
	}
	
	public Object nextLastStack() {
		return stack[stack.length-2];
	}
	
	public Object[] lastTwoStack() {
		return new Object[] {nextLastStack(), lastStack()};
	}
}
