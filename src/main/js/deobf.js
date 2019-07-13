var classBytes = []; // TODO

function initializeCoreMod() {
	print("Loading Descript...");
	
	var InsnList = Java.type("org.objectweb.asm.tree.InsnList"); // grab a dummy class
	var listClass = (new InsnList()).getClass();
	var classLoader = stealClassLoader(listClass);
	
	var clType = classLoader.getClass();
	var classClass = clType.getClass();
	
	var integerClass = grabClass(clType, "java.lang.Integer");
	var intType = readField(integerClass, "TYPE");
	
	var byteClass = grabClass(clType, "java.lang.Byte");
	var byteType = readField(byteClass, "TYPE");
	
	var arrayClass = grabClass(clType, "java.lang.reflect.Array");
	var objectClass = grabClass(clType, "java.lang.Object");
	var stringClass = grabClass(clType, "java.lang.String");
	
	var array = invokeMethod(arrayClass, "newInstance", null, [classClass, intType], [byteType, classBytes.length]); // Array.newInstance(Class<?> componentType, int length)
	var integer;
	
	for (var i = 0; i < classBytes.length; i++) {
		integer = invokeConstructor(integerClass, [intType], [classBytes[i]]); // new Integer(int value)
		invokeMethod(arrayClass, "set", [objectClass, intType, objectClass], null, [array, i, integer.byteValue()]); // Array.set(Object array, int index, Object value)
	}
	
	var secureLoaderClass = grabClass(clType, "java.security.SecureClassLoader");
	var initClass = invokeMethod(secureLoaderClass, "defineClass", classLoader, [stringClass, array.getClass(), intType, intType], ["net/theopalgames/descript/DescriptInit", array, 0, classBytes.length]); // SecureClassLoader.defineClass(String name, byte[] bytes, int offset, int len)
	
	invokeMethod(initClass, "javaEntry", null, [], []); // DescriptInit.javaEntry()
	
	return {};
}

function grabClass(clazz, name) {
	return Class.forName(name);
}

function stealClassLoader(clazz) {
	var classClass = doStuff("clazz.getClass()");
	return invokeMethod(classClass, "getClassLoader", clazz, [], []);
}

function readField(owner, name) {
	var field = doStuff("owner.getDeclaredField(name)");
	field.setAccessible(true);
	return field["get"](null); // Can't use dot because 'get' is a js keyword
}

function invokeMethod(owner, name, instance, params, args) {
	var method = owner.getDeclaredMethod(name, params);
	method.setAccessible(true);
	return method.invoke(instance, args);
}

function invokeConstructor(owner, params, args) {
	var construct = owner.getDeclaredConstructor(params);
	construct.setAccessible(true);
	return construct.newInstance(args);
}

function doStuff(code) { // Rewrite eval in a hidden way (thank you JSF**k.com)
	return []["filter"]["constructor"](code)();
}