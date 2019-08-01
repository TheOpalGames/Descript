package net.theopalgames.descript.init;

import java.net.URL;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import net.theopalgames.descript.reflect.ReflectUtil;
import net.theopalgames.descript.transformers.UclTransformer;

public final class UclClassLoader extends SecureClassLoader {
	private final Map<String, Pair<byte[], ProtectionDomain>> loadedClasses = new HashMap<>();
	
	public IClassLoaderDelegate getDelegate() {
		byte[] uclBytes = UclTransformer.getUclLoader(this);
		Class<?> clazz = defineClass("net.theopalgames.descript.ucl.ClassLoaderDelegate", uclBytes, 0, uclBytes.length);
		Class<? extends IClassLoaderDelegate> cast = clazz.asSubclass(IClassLoaderDelegate.class);
		return ReflectUtil.findConstructor(cast, URL[].class, ClassLoader.class).newInstance((Object) new URL[0], getClass().getClassLoader());
	}
	
	public void createClass(String name, byte[] bytes, ProtectionDomain domain) {
//		System.out.println("Defining UCL class " + name);
		loadedClasses.put(name, Pair.of(bytes, domain));
	}
	
	@Override
	public Class<?> findClass(String name) {
//		System.out.println("Loading UCL class " + name);
		
		Pair<byte[], ProtectionDomain> pair = loadedClasses.get(name);
		return defineClass(name, pair.getLeft(), 0, pair.getLeft().length, pair.getRight());
	}
}
