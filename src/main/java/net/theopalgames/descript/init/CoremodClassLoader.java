package net.theopalgames.descript.init;

import java.net.URL;
import java.security.CodeSource;
import java.security.SecureClassLoader;

import org.apache.commons.lang3.tuple.Pair;

import cpw.mods.modlauncher.TransformingClassLoader;
import net.minecraftforge.fml.loading.FMLLoader;

public final class CoremodClassLoader extends SecureClassLoader {
	private final IClassLoaderDelegate delegate;
	private final ClassLoader parent;
	
	public static final TransformingClassLoader gameLoader = FMLLoader.getLaunchClassLoader();
	
	public CoremodClassLoader() {
		super(null);
		
		parent = getClass().getClassLoader();
		delegate = new UclClassLoader().getDelegate();
	}
	
	public void addURL(URL url) {
		delegate.addURL(url);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
//		System.out.println("Loading coremod class: " + name);
		
		if (name.startsWith("net.theopalgames.descript.init."))
			return parent.loadClass(name);
		else if (name.startsWith("net.theopalgames.descript.ucl."))
			return delegate.getClass().getClassLoader().loadClass(name);
		
		try {
			Pair<byte[], CodeSource> pair = delegate.findClass(name);
			byte[] basicClass = pair.getLeft();
			CodeSource source = pair.getRight();
			
	//		Result result = SerializationTransformer.transform(basicClass);
	//		if (result.serialize)
	//			CoreModLoader.transformers.serialize.add(name);
	//		
	//		return defineClass(name, result.transformed, 0, result.transformed.length, source);
			return defineClass(name, basicClass, 0, basicClass.length, source);
		} catch (ClassNotFoundException e) {
			return gameLoader.loadClass(name);
		}
	}
}
