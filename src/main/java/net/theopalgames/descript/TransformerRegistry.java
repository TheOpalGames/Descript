package net.theopalgames.descript;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformStore;
import cpw.mods.modlauncher.TransformationServiceDecorator;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.theopalgames.descript.api.ITransformerRegistry;
import net.theopalgames.descript.api.launchwrapper.IClassTransformer;
import net.theopalgames.descript.reflect.ReflectUtil;
import net.theopalgames.descript.transformers.LwWrapperTransformer;

final class TransformerRegistry implements ITransformerRegistry {
	private final TransformStore cpwRegistry = ReflectUtil.readField(Launcher.class, "transformStore", Launcher.INSTANCE);
	final Set<String> serialize = new HashSet<>();
	private final DescriptTransformService service = new DescriptTransformService();
	
	@Override
	public void registerModLauncherTransformer(ITransformer<?> transformer) {
		service.registerTransformer(transformer);
	}
	
	@Override
	public void registerLaunchWrapperTransformer(IClassTransformer transformer) {
		registerModLauncherTransformer(new LwWrapperTransformer(transformer, true));
	}
	
	void outputTransformers() {
		TransformationServiceDecorator decorator = ReflectUtil.findConstructor(TransformationServiceDecorator.class, ITransformationService.class).newInstance(service);
		decorator.gatherTransformers(cpwRegistry);
	}
}
