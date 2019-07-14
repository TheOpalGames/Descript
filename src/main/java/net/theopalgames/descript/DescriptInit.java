package net.theopalgames.descript;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.Nullable;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraftforge.coremod.CoreMod;
import net.minecraftforge.coremod.CoreModEngine;
import net.minecraftforge.coremod.CoreModProvider;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;

// WARNING: If you make any changes to this file, you need to recompile it and put the bytes into src/main/js/core-plugin.js

@UtilityClass
public class DescriptInit {
	public void javaEntry() throws Exception {
		File descriptJar = findDescriptJar();
		URL url = descriptJar.toURI().toURL();
		
		URLClassLoader ucl = new URLClassLoader(new URL[] {url});
		Class<?> loader = Class.forName("net.theopalgames.descript.CoreModLoader", true, ucl);
		
		Method loadDescriptPlugins = loader.getDeclaredMethod("loadDescriptPlugins");
		loadDescriptPlugins.invoke(null);
	}
	
	private File findDescriptJar() throws Exception {
		CoreModProvider provider = (CoreModProvider) FMLLoader.getCoreModProvider();
		CoreModEngine engine = readField(CoreModProvider.class, "engine", provider);
		
		List<CoreMod> coreMods = readField(CoreModEngine.class, "coreMods", engine);
		CoreModFile fileWrapper;
		File file = null;
		JarEntry entry;
		
		for (CoreMod coreMod : coreMods) {
			fileWrapper = readField(CoreMod.class, "file", coreMod);
			file = fileWrapper.getPath().toFile();
			
			try (JarFile jar = new JarFile(file)) {
				entry = jar.getJarEntry(DescriptInit.class.getName().replace('.', '/') + ".class");
				if (entry != null)
					break;
			}
		}
		
		if (file == null)
			throw new IllegalStateException("Descript somehow loaded without being a coremod...");
		
		return file;
	}
	
	// From ReflectUtil.java
	
	@SuppressWarnings("unchecked")
	@SneakyThrows
	private <T> T readField(Class<?> owner, String name, @Nullable Object instance) {
		Field field = getDeclaredField(owner, name);
		return (T) field.get(instance);
	}
	
	private Field getDeclaredField(Class<?> owner, String name) throws Exception {
		Field field = owner.getDeclaredField(name);
		field.setAccessible(true);
		return field;
	}
}
