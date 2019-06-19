package net.theopalgames.descript;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformStore;
import cpw.mods.modlauncher.TransformationServiceDecorator;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.theopalgames.descript.api.ITransformerRegistry;

final class TransformerRegistry implements ITransformerRegistry {
	private final TransformStore cpwRegistry = ReflectUtil.readField(Launcher.class, "transformStore", Launcher.INSTANCE);
	private final DescriptTransformService service = new DescriptTransformService();
	
	@Override
	public void registerCpwTransformer(ITransformer<?> transformer) {
		service.registerTransformer(transformer);
	}
	
	void outputTransformers() {
		TransformationServiceDecorator decorator = ReflectUtil.findConstructor(TransformationServiceDecorator.class, ITransformationService.class).newInstance(service);
		decorator.gatherTransformers(cpwRegistry);
	}
}
