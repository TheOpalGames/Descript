package net.theopalgames.descript.init;

import java.net.URL;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;

import net.theopalgames.descript.reflect.ReflectUtil;
import net.theopalgames.descript.transformers.UclTransformer;

public final class UclClassLoader extends SecureClassLoader {
	public IClassLoaderDelegate getDelegate() {
		byte[] uclBytes = UclTransformer.getUclLoader(this);
		Class<?> clazz = defineClass("net.theopalgames.descript.ucl.ClassLoaderDelegate", uclBytes, 0, uclBytes.length);
		Class<? extends IClassLoaderDelegate> cast = clazz.asSubclass(IClassLoaderDelegate.class);
		return ReflectUtil.findConstructor(cast, URL[].class, ClassLoader.class).newInstance((Object) new URL[0], getClass().getClassLoader());
	}
	
	public Class<?> createClass(String name, byte[] bytes, ProtectionDomain domain) {
		return defineClass(name, bytes, 0, bytes.length, domain);
	}
}