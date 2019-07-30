package net.theopalgames.descript.init;

import java.io.File;

import lombok.experimental.UtilityClass;
import net.theopalgames.descript.reflect.ReflectUtil;

@UtilityClass
public class CoremodBootstrap {
	public final CoremodClassLoader classLoader = new CoremodClassLoader();
	
	public void init(File descriptJar) throws Exception {
		classLoader.addURL(descriptJar.toURI().toURL());
		
		Class<?> loader = Class.forName("net.theopalgames.descript.coremods.CoreModLoader", true, classLoader);
		ReflectUtil.findMethod(loader, "loadDescriptPlugins").call(null);
	}
}
