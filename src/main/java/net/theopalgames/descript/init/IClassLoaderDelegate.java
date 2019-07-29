package net.theopalgames.descript.init;

import java.net.URL;
import java.security.CodeSource;

import org.apache.commons.lang3.tuple.Pair;

public interface IClassLoaderDelegate {
	public abstract void addURL(URL url);
	
	public abstract Pair<byte[], CodeSource> findClass(String name) throws ClassNotFoundException;
}
