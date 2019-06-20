package net.theopalgames.descript.asm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Frame {
	public final int nLocal;
	public final Object[] local;
	public final int nStack;
	public final Object[] stack;
}
