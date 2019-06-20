package net.theopalgames.descript;

import java.net.URL;
import java.security.CodeSource;

public interface IClassLoaderDelegate {
	public abstract void addURL(URL url);
	
	public abstract byte[] getBytes(String className) throws ClassNotFoundException;
	public abstract CodeSource getCodeSource(String className);
}
