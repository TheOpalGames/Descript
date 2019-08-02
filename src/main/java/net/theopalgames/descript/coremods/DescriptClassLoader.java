package net.theopalgames.descript.coremods;

public final class DescriptClassLoader extends ClassLoader {
	public DescriptClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return getClass().getClassLoader().loadClass(name);
	}
}
