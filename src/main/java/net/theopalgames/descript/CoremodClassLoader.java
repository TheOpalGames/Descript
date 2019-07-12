package net.theopalgames.descript;

import java.net.URL;
import java.security.CodeSource;
import java.security.SecureClassLoader;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.TransformingClassLoader;
import net.theopalgames.descript.reflect.ReflectUtil;

public final class CoremodClassLoader extends SecureClassLoader {
	private final IClassLoaderDelegate delegate;
	private final ClassLoader parent;
	
	public static final TransformingClassLoader gameLoader = ReflectUtil.readField(Launcher.class, "classLoader");
	
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
		int lastDot = name.lastIndexOf('.');
		
		if (name.substring(0, lastDot).equals("net.theopalgames.descript"))
			return parent.loadClass(name);
		
		try {
			byte[] basicClass = delegate.getBytes(name);
			CodeSource source = delegate.getCodeSource(name);
			
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
