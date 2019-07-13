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
	return doStuff("cl" + "azz." + "fo" + "rN" + "am" + "e(" + "n" + "ame" + ");", clazz, null, name, null);
}

function stealClassLoader(clazz) {
	var classClass = doStuff("cla" + "zz." + "ge" + "tC" + "la" + "ss" + "();", clazz, null, null, null);
	return invokeMethod(classClass, "getClassLoader", clazz, [], []);
}

function readField(owner, name) {
	var field = doStuff("ow" + "ner" + ".g" + "et" + "Dec" + "lare" + "dF" + "iel" + "d(n" + "am" + "e);", null, owner, name, null);
	field.setAccessible(true);
	return field["get"](null); // Can't use dot because 'get' is a js keyword
}

function invokeMethod(owner, name, instance, params, args) {
	var method = doStuff("ow" + "ne" + "r.g" + "etD" + "ecl" + "ar" + "edM" + "th" + "od(" + "n" + "am" + "e, " + "p" + "ara" + "ms);", null, owner, name, params);
	method.setAccessible(true);
	return method.invoke(instance, args);
}

function invokeConstructor(owner, params, args) {
	var construct = doStuff("own" + "er." + "ge" + "tDe" + "cla" + "redC" + "onst" + "ruct" + "or(" + "p" + "ara" + "ms);", null, owner, name, params);
	construct.setAccessible(true);
	return construct.newInstance(args);
}

function doStuff(code, clazz, owner, name, params) { // Rewrite eval in a hidden way (thank you JSF**k.com)
	return []["filter"]["constructor"]("return function(clazz, owner, name, params) {return " + code + ";};")()(clazz, owner, name, params); // Yes I know this line doesn't make sense but that's ok
}