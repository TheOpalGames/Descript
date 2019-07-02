package net.theopalgames.descript;

import java.net.URL;
import java.security.CodeSource;
import java.security.SecureClassLoader;

import net.theopalgames.descript.transformers.SerializationTransformer;
import net.theopalgames.descript.transformers.SerializationTransformer.Result;

public final class CoremodClassLoader extends SecureClassLoader {
	private final IClassLoaderDelegate delegate;
	
	public CoremodClassLoader() {
		delegate = new UclClassLoader().getDelegate();
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
