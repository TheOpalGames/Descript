package net.theopalgames.descript;

import java.net.URL;
import java.net.URLClassLoader;

public final class CoremodClassLoader extends URLClassLoader {
	public CoremodClassLoader() {
		super(new URL[0]);
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
}
