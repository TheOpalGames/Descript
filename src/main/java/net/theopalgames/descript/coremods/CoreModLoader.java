package net.theopalgames.descript.coremods;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import cpw.mods.modlauncher.TransformingClassLoader;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.theopalgames.descript.DescriptCoreMod;
import net.theopalgames.descript.api.CoreMod;
import net.theopalgames.descript.api.IDescriptModInfo;
import net.theopalgames.descript.containers.DescriptBaseContainer;
import net.theopalgames.descript.containers.DescriptFmlContainer;
import net.theopalgames.descript.init.CoremodBootstrap;
import net.theopalgames.descript.init.CoremodClassLoader;
import net.theopalgames.descript.reflect.ReflectUtil;

@UtilityClass
public class CoreModLoader {
	private final Name DESCRIPT_CORE_MOD = new Name("Descript-Core-Mod");
	
	private final List<File> toRemove = new ArrayList<>();
	final TransformerRegistry transformers = new TransformerRegistry();
	private final List<ModContainer> containers = new ArrayList<>();
	
	public void loadDescriptPlugins() {
		loadCoreMod(new DescriptCoreMod());
		
		swapClassFinder();
		
		LoadingModList mods = ReflectUtil.readField(FMLLoader.class, "loadingModList");
		mods.getModFiles().stream().map(ModFileInfo::getFile).map(ModFile::getFilePath).map(Path::toUri).forEach(CoreModLoader::processFile);
		
		transformers.outputTransformers();
	}
	
	private void swapClassFinder() {
		Function<String, URL> oldFinder = ReflectUtil.readField(TransformingClassLoader.class, "classBytesFinder", CoremodClassLoader.gameLoader);
		Function<String, URL> newFinder = oldFinder.andThen(url -> (isDescript(url) ? null : url));
		
		ReflectUtil.writeField(TransformingClassLoader.class, "classBytesFinder", CoremodClassLoader.gameLoader, newFinder);
	}
	
	private boolean isDescript(URL url) {
		return url == null || url.getHost().equals("descript-dummy");
	}
	
	@SneakyThrows
	private void processFile(URI uri) {
		File file = new File(uri);
		
		if (file.isDirectory())
			modDir(file);
		else if (file.isFile() && file.getName().endsWith(".jar"))
			modJar(file);
	}
	
	private void modDir(File dir) throws Exception {
		File manifestFile = new File(dir, "META-INF/MANIFEST.MF");
		if (!manifestFile.exists())
			return;
		
		Manifest manifest = new Manifest(new FileInputStream(manifestFile));
		scanCoreMod(dir, manifest);
	}
	
	private void modJar(File file) throws Exception {
		try (JarFile jar = new JarFile(file)) {
			Manifest manifest = jar.getManifest();
			scanCoreMod(file, manifest);
		}
	}
	
	private void scanCoreMod(File file, Manifest manifest) throws Exception {
		String name = manifest.getMainAttributes().getValue(DESCRIPT_CORE_MOD);
		if (name == null)
			return;
		
		toRemove.add(file);
		CoremodBootstrap.classLoader.addURL(file.toURI().toURL());
		
		Class<?> clazz = Class.forName(name, true, CoremodBootstrap.classLoader);
		Class<? extends CoreMod> cast = clazz.asSubclass(CoreMod.class);
		
		CoreMod coreMod = cast.newInstance();
		loadCoreMod(coreMod);
	}
	
	private void loadCoreMod(CoreMod coreMod) {
		coreMod.injectTransformers(transformers);
		
		IDescriptModInfo info = coreMod.getModInfo();
		if (info != null)
			containers.add(info.getMain() == null ? new DescriptBaseContainer(info) : new DescriptFmlContainer(info));
	}
	
	public List<ModContainer> getContainers() {
		return Collections.unmodifiableList(containers);
	}
}
