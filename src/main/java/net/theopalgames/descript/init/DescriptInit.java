package net.theopalgames.descript.init;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.annotation.Nullable;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import com.google.common.io.ByteStreams;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraftforge.coremod.CoreMod;
import net.minecraftforge.coremod.CoreModEngine;
import net.minecraftforge.coremod.CoreModProvider;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.CoreModFile;

// WARNING: No warning anymore lol

@UtilityClass
public class DescriptInit {
	public void javaEntry() throws Exception {
		File descriptJar = findDescriptJar();
		URL url = descriptJar.toURI().toURL();
		
		URLClassLoader ucl = new URLClassLoader(new URL[] {url});
		Class<?> loader = Class.forName("net.theopalgames.descript.init.CoremodBootstrap", true, ucl);
		
		Method loadDescriptPlugins = loader.getDeclaredMethod("init", File.class);
		loadDescriptPlugins.invoke(null, descriptJar);
	}
	
	private File findDescriptJar() throws Exception {
		CoreModProvider provider = (CoreModProvider) FMLLoader.getCoreModProvider();
		CoreModEngine engine = readField(CoreModProvider.class, "engine", provider);
		
		List<CoreMod> coreMods = readField(CoreModEngine.class, "coreMods", engine);
		CoreModFile fileWrapper;
		Path path;
		FileSystem fs;
		File file = null;
		JarEntry entry;
		byte[] bytes;
		ClassNode clazz;
		
		for (CoreMod coreMod : coreMods) {
			fileWrapper = readField(CoreMod.class, "file", coreMod);
			path = fileWrapper.getPath();
			fs = path.getFileSystem();
			
			if (fs.getClass().getName().endsWith(".ZipFileSystem"))
				path = readField(fs.getClass(), "zfpath", fs);
			
			file = path.toFile();
			
			if (file.isFile()) {
				if (file.getName().endsWith(".jar"))
					try (JarFile jar = new JarFile(file)) {
						entry = jar.getJarEntry(DescriptInit.class.getName().replace('.', '/') + ".class");
						if (entry != null)
							break;
					}
				else if (file.getName().endsWith(".class")) { // dev env and stuff
					try (FileInputStream in = new FileInputStream(file)) {
						bytes = new byte[(int) file.length()];
						ByteStreams.readFully(in, bytes); // TODO: Stop using Google Guava because Google sucks and DuckDuckGo is better
						
						clazz = new ClassNode();
						new ClassReader(bytes).accept(clazz, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
						
						if (clazz.name.equals(DescriptInit.class.getName().replace('.', '/')))
							break;
					}
				}
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
