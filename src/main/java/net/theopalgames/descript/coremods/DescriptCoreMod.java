package net.theopalgames.descript.coremods;

import net.theopalgames.descript.api.CoreMod;
import net.theopalgames.descript.api.IDescriptModInfo;
import net.theopalgames.descript.api.ITransformerRegistry;
import net.theopalgames.descript.transformers.ModListTransformer;
import net.theopalgames.descript.transformers.ModLoaderTransformer;

public final class DescriptCoreMod extends CoreMod {
	@Override
	public void injectTransformers(ITransformerRegistry registry) {
		registry.registerModLauncherTransformer(new ModLoaderTransformer());
		registry.registerModLauncherTransformer(new ModListTransformer());
	}
	
	@Override
	public IDescriptModInfo getModInfo() {
		return new DescriptInfo();
	}
}
