package net.theopalgames.descript.init;

import java.net.URL;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.SneakyThrows;
import net.theopalgames.descript.reflect.ReflectUtil;
import net.theopalgames.descript.transformers.UclTransformer;

public final class UclClassLoader extends SecureClassLoader {
	private final Map<String, Pair<byte[], ProtectionDomain>> loadedClasses = new HashMap<>();
	
	@SneakyThrows
	public IClassLoaderDelegate getDelegate() {
//		System.out.println("Defining ClassLoaderDelegate...");
		
		byte[] uclBytes = UclTransformer.getUclLoader(this);
		createClass("net.theopalgames.descript.ucl.ClassLoaderDelegate", uclBytes, null);
		
		Class<?> clazz = Class.forName("net.theopalgames.descript.ucl.ClassLoaderDelegate", true, this);
		Class<? extends IClassLoaderDelegate> cast = clazz.asSubclass(IClassLoaderDelegate.class);
		return ReflectUtil.findConstructor(cast, URL[].class, ClassLoader.class).newInstance((Object) new URL[0], getClass().getClassLoader());
	}
	
	public void createClass(String name, byte[] bytes, ProtectionDomain domain) {
//		System.out.println("Defining UCL class " + name);
		loadedClasses.put(name, Pair.of(bytes, domain));
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		if (name.equals("net.theopalgames.descript.init.IClassLoaderDelegate") || name.equals("net.theopalgames.descript.transformers.UclTransformer"))
			return getClass().getClassLoader().loadClass(name);
		
//		System.out.println("Loading UCL class " + name);
		
		Pair<byte[], ProtectionDomain> pair = loadedClasses.get(name);
		
//		if (pair == null)
//			throw new ClassNotFoundException(name, new NullPointerException("pair"));
		
		try {
			return defineClass(name, pair.getLeft(), 0, pair.getLeft().length, pair.getRight());
		} catch (NullPointerException e) {
			throw new ClassNotFoundException(name, e);
		}
	}
}
