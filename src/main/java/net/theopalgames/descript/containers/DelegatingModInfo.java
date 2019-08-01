package net.theopalgames.descript.containers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.versioning.ArtifactVersion;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;

import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;

public final class DelegatingModInfo extends ModInfo {
	private static final Config DEFAULT_CONFIG;
	private static final Map<IModInfo, DelegatingModInfo> INFO_MAP = new HashMap<>();
	
	static {
		DEFAULT_CONFIG = Config.inMemory();
		DEFAULT_CONFIG.set("modId", "broken-descript-mod");
		DEFAULT_CONFIG.set("description", "Uh oh...something went wrong in Descript...get it? Descript, description? Anyway, you should report it.");
	}
	
	public static DelegatingModInfo create(IModInfo delegate) {
		return INFO_MAP.computeIfAbsent(delegate, d -> new DelegatingModInfo(d));
	}
	
	private final IModInfo delegate;
	
	private DelegatingModInfo(IModInfo delegate) {
		super(null, findConfig(delegate));
		this.delegate = delegate;
	}
	
	private static UnmodifiableConfig findConfig(IModInfo info) {
		UnmodifiableConfig config = info.getModConfig();
		if (config != null)
			return config;
		
		return DEFAULT_CONFIG;
	}
	
	@Override
	public String getModId() {
		return delegate.getModId();
	}
	
	@Override
	public String getDisplayName() {
		return delegate.getDisplayName();
	}
	
	@Override
	public String getNamespace() {
		return delegate.getNamespace();
	}
	
	@Override
	public List<ModVersion> getDependencies() {
		return delegate.getDependencies();
	}
	
	@Override
	public String getDescription() {
		return delegate.getDescription();
	}
	
	@Override
	public ArtifactVersion getVersion() {
		return delegate.getVersion();
	}
	
	@Override
	public URL getUpdateURL() {
		return delegate.getUpdateURL();
	}
}
