package net.theopalgames.descript.init;

import java.net.URL;

import lombok.experimental.UtilityClass;
import net.theopalgames.descript.reflect.ReflectUtil;

@UtilityClass
public class CoremodBootstrap {
	public final CoremodClassLoader classLoader = new CoremodClassLoader();
	
	public void init() throws Exception {
		URL ourJar = CoremodBootstrap.class.getProtectionDomain().getCodeSource().getLocation();
		classLoader.addURL(ourJar);
		
		Class<?> loader = Class.forName("net.theopalgames.descript.coremods.CoreModLoader", true, classLoader);
		ReflectUtil.findMethod(loader, "loadDescriptPlugins").call(null);
	}
}
