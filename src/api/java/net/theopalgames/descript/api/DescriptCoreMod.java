package net.theopalgames.descript.api;

import net.minecraftforge.fml.ModContainer;

public abstract class DescriptCoreMod {
	public void injectTransformers(ITransformerRegistry registry) {
		// NOOP
	}
	
	public ModContainer getModContainer() {
		return null;
	}
}
