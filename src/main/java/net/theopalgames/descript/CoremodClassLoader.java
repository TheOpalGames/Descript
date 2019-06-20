package net.theopalgames.descript;

import java.net.URL;
import java.security.CodeSource;
import java.security.SecureClassLoader;

import net.theopalgames.descript.reflect.ReflectUtil;
import net.theopalgames.descript.transformers.SerializationTransformer;
import net.theopalgames.descript.transformers.SerializationTransformer.Result;
import net.theopalgames.descript.transformers.UclTransformer;

public final class CoremodClassLoader extends SecureClassLoader {
	private final IClassLoaderDelegate delegate;
	
	public CoremodClassLoader() {
		byte[] uclBytes = UclTransformer.getUclLoader();
		Class<?> clazz = defineClass("net.theopalgames.descript.ClassLoaderDelegate", uclBytes, 0, uclBytes.length);
		Class<? extends IClassLoaderDelegate> cast = clazz.asSubclass(IClassLoaderDelegate.class);
		delegate = ReflectUtil.findConstructor(cast, URL[].class).newInstance((Object) new URL[0]);
	}
	
	public void addURL(URL url) {
		delegate.addURL(url);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] basicClass = delegate.getBytes(name);
		CodeSource source = delegate.getCodeSource(name);
		
		Result result = SerializationTransformer.transform(basicClass);
		if (result.serialize)
			CoreModLoader.transformers.serialize.add(name);
		
		return defineClass(name, result.transformed, 0, result.transformed.length, source);
	}
}
