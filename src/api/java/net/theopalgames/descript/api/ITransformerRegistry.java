package net.theopalgames.descript.api;

import cpw.mods.modlauncher.api.ITransformer;
import net.theopalgames.descript.api.launchwrapper.IClassTransformer;

public interface ITransformerRegistry {
	public abstract void registerModLauncherTransformer(ITransformer<?> transformer);
	
	public abstract void registerLaunchWrapperTransformer(IClassTransformer transformer);
}
