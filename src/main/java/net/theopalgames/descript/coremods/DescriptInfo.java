package net.theopalgames.descript.coremods;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.UnmodifiableConfig;

import net.minecraftforge.forgespi.language.IModFileInfo;
import net.theopalgames.descript.api.IDescriptModInfo;

public final class DescriptInfo implements IDescriptModInfo {
	public static final String MODID = "descript";
	public static final String NAME = "Descript";
	public static final String VERSION = "1.0";
	
	private static final Config CONFIG = Config.inMemory();
	
	static {
		CONFIG.set("modId", MODID);
		CONFIG.set("authors", "Hallowizer");
	}
	
	@Override
	public IModFileInfo getOwningFile() {
		return null; // Unused method
	}
	
	@Override
	public String getModId() {
		return MODID;
	}
	
	@Override
	public String getDisplayName() {
		return NAME;
	}
	
	@Override
	public String getDescription() {
		return "Minecraft mod to bring back pre-1.13 features in the newer versions of Forge.";
	}
	
	@Override
	public ArtifactVersion getVersion() {
		return new DefaultArtifactVersion(VERSION);
	}
	
	@Override
	public List<ModVersion> getDependencies() {
		return Collections.emptyList();
	}
	
	@Override
	public UnmodifiableConfig getModConfig() {
		return CONFIG;
	}
	
	@Override
	public String getNamespace() {
		return "descript";
	}
	
	@Override
	public Map<String, Object> getModProperties() {
		return Collections.emptyMap();
	}
	
	@Override
	public URL getUpdateURL() {
		return null; // No auto updates for now...
	}
	
	@Override
	public String getMain() {
		return null;
	}
}
