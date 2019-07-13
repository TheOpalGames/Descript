var bytes = []; // TODO: Add bytecode

function initializeCoreMod() {
	print("Loading Descript...");
	
	var byteType = java.lang.Byte.TYPE;
	var javaBytes = new (Java.type("byte[]"))(bytes.length);
	
	var integer;
	for (var i = 0; i < bytes.length; i++) {
		java.lang.reflect["Array"]["set"](javaBytes, i, bytes[i]);
	}
	
	var customLoaderType = Java.extend(Java.type("java.security.SecureClassLoader"), {
		createClass: function(name, bytecode, length) {
			return this.defineClass(name, bytecode, 0, length);
		}
	});
	
	var cl = new customLoaderType();
	var init = cl.createClass("net.theopalgames.descript.DescriptInit", javaBytes, bytes.length);
	var instance = init.newInstance();
	
	instance.javaEntry();
	return {};
}