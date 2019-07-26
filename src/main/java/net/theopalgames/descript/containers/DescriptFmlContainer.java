package net.theopalgames.descript.containers;

import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.theopalgames.descript.api.IDescriptModInfo;
import net.theopalgames.desript.init.CoremodClassLoader;

public class DescriptFmlContainer extends FMLModContainer {
	public DescriptFmlContainer(IDescriptModInfo info) {
		super(info, info.getMain(), CoremodClassLoader.gameLoader, null);
	}
}
