package net.theopalgames.descript.containers;

import java.util.HashMap;
import java.util.Map;

import com.electronwill.nightconfig.core.Config;

import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;

public final class DelegatingModInfo extends ModInfo {
	private static final Config CONFIG;
	private static final Map<IModInfo, DelegatingModInfo> INFO_MAP = new HashMap<>();
	
	static {
		CONFIG = Config.inMemory();
		CONFIG.set("modId", "broken-descript-mod");
		CONFIG.set("description", "Uh oh...something went wrong in Descript...get it? Descript, description? Anyway, you should report it.");
	}
	
	public static DelegatingModInfo create(IModInfo delegate) {
		return INFO_MAP.computeIfAbsent(delegate, d -> new DelegatingModInfo(d));
	}
	
	private DelegatingModInfo(IModInfo delegate) {
		super(null, CONFIG);
	}
}
