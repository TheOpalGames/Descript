package net.theopalgames.descript.containers;

import net.minecraftforge.fml.ModContainer;
import net.theopalgames.descript.api.IDescriptModInfo;

public class DescriptBaseContainer extends ModContainer {
	private final Object mod = new Object();
	
	public DescriptBaseContainer(IDescriptModInfo info) {
		super(info);
	}
	
	@Override
	public boolean matches(Object mod) {
		return getMod().equals(mod);
	}
	
	@Override
	public Object getMod() {
		return mod;
	}
}
