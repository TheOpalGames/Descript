package net.theopalgames.descript.api;

public abstract class CoreMod {
	public void injectTransformers(ITransformerRegistry registry) {
		// NOOP
	}
	
	public IDescriptModInfo getModInfo() {
		return null;
	}
}
