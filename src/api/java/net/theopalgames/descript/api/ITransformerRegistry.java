package net.theopalgames.descript.api;

import cpw.mods.modlauncher.api.ITransformer;

public interface ITransformerRegistry {
	public abstract void registerCpwTransformer(ITransformer<?> transformer);
}
