package net.theopalgames.descript.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.IncompatibleEnvironmentException;

public final class DescriptTransformService implements ITransformationService {
	@SuppressWarnings("rawtypes")
	private final List<ITransformer> transformers = new ArrayList<>();
	
	@Override
	public String name() {
		return "descript";
	}
	
	@Override
	public void initialize(IEnvironment environment) {
		// NOOP
	}
	
	@Override
	public void beginScanning(IEnvironment environment) {
		// NOOP
	}
	
	@Override
	public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException {
		// NOOP
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<ITransformer> transformers() {
		return transformers;
	}
	
	void registerTransformer(ITransformer<?> transformer) {
		transformers.add(transformer);
	}
}
