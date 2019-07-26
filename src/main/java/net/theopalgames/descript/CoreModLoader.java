package net.theopalgames.descript;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.LoadingModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.loading.moddiscovery.ModFileInfo;
import net.theopalgames.descript.api.CoreMod;
import net.theopalgames.descript.api.IDescriptModInfo;
import net.theopalgames.descript.containers.DescriptBaseContainer;
import net.theopalgames.descript.containers.DescriptFmlContainer;
import net.theopalgames.descript.reflect.ReflectUtil;

@UtilityClass
public class CoreModLoader {
	private final Name DESCRIPT_CORE_MOD = new Name("Descript-Core-Mod");
	
	private final CoremodClassLoader classLoader = new CoremodClassLoader();
	private final List<File> toRemove = new ArrayList<>();
	final TransformerRegistry transformers = new TransformerRegistry();
	private final List<ModContainer> containers = new ArrayList<>();
	
	public void loadDescriptPlugins() {
		loadCoreMod(new DescriptCoreMod());
		
		LoadingModList mods = ReflectUtil.readField(FMLLoader.class, "loadingModList");
		mods.getModFiles().stream().map(ModFileInfo::getFile).map(ModFile::getFilePath).map(Path::toUri).forEach(CoreModLoader::processFile);
		
		transformers.outputTransformers();
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
		classLoader.addURL(file.toURI().toURL());
		
		Class<?> clazz = Class.forName(name, true, classLoader);
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
