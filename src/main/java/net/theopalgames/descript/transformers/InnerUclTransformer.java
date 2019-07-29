package net.theopalgames.descript.transformers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLClassLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import lombok.experimental.UtilityClass;
import net.theopalgames.descript.init.UclClassLoader;

@UtilityClass
public class InnerUclTransformer {
	public void loadBytes(String name, UclClassLoader classLoader, String clPackage) throws Exception {
//		System.out.println("Transforming inner class: " + name);
		
		if (!name.startsWith("java/net/"))
			return;
		
		InputStream in = URLClassLoader.class.getResourceAsStream("/" + name + ".class");
		
		byte[] bytes;
		try (BufferedInputStream bin = new BufferedInputStream(in)) {
			bytes = new byte[bin.available()];
			bin.read(bytes);
		}
		
		ClassNode clazz = new ClassNode();
		new ClassReader(bytes).accept(clazz, ClassReader.SKIP_FRAMES);
		
		clazz.name = clazz.name.replace("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate");
		
		for (MethodNode method : clazz.methods) {
			Type type = Type.getMethodType(method.desc);
			Type[] args = type.getArgumentTypes();
			
			for (int i = 0; i < args.length; i++) {
				if (args[i].getDescriptor().equals("Ljava/net/URLClassLoader;"))
					args[i] = Type.getType("Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;");
			}
			
			for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode cast = (MethodInsnNode) insn;
					
					if (cast.owner.equals("java/net/URLClassLoader")) {
						cast.owner = "net/theopalgames/descript/ucl/ClassLoaderDelegate";
					
						if (cast.name.equals("defineClass"))
							cast.desc = "(Ljava/lang/String;L" + clPackage + "/Resource;)Lorg/apache/commons/lang3/tuple/Pair;";
					}
				} else if (insn instanceof FieldInsnNode) {
					FieldInsnNode cast = (FieldInsnNode) insn;
					
					if (cast.owner.equals("java/net/URLClassLoader"))
						cast.owner = "net/theopalgames/descript/ucl/ClassLoaderDelegate";
				}
			
			Type newType = Type.getMethodType(type.getReturnType(), args);
			method.desc = newType.getDescriptor();
		}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		clazz.accept(cw);
		byte[] newBytes = cw.toByteArray();
		
		Class<?> oldClass = Class.forName(name.replace('/', '.'));
		Class<?> newClass = classLoader.createClass(clazz.name.replace('/', '.'), newBytes, oldClass.getProtectionDomain());
		newClass.getName(); // Make sure it fully initializes
	}
}
