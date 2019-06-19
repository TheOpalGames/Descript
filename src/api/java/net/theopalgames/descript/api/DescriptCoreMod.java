package net.theopalgames.descript.api;

import net.minecraftforge.fml.ModContainer;

public abstract class DescriptCoreMod {
	public abstract void injectTransformers(ITransformerRegistry registry);
	
	public ModContainer getModContainer() {
		return null;
	}
}
