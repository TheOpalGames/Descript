package net.theopalgames.descript.transformers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLClassLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import lombok.experimental.UtilityClass;
import net.theopalgames.descript.UclClassLoader;

@UtilityClass
public class InnerUclTransformer {
	public void loadBytes(String name, UclClassLoader classLoader) throws Exception {
		InputStream in = URLClassLoader.class.getResourceAsStream("/" + name);
		
		byte[] bytes;
		try (BufferedInputStream bin = new BufferedInputStream(in)) {
			bytes = new byte[bin.available()];
			bin.read(bytes);
		}
		
		ClassNode clazz = new ClassNode();
		new ClassReader(bytes).accept(clazz, ClassReader.SKIP_FRAMES);
		
		clazz.name = clazz.name.replace("java/net/", "net/theopalgames/descript/ucl/");
		
		for (MethodNode method : clazz.methods)
			for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode cast = (MethodInsnNode) insn;
					
					if (cast.owner.equals("java/net/URLClassLoader"))
						cast.owner = "net/theopalgames/descript/ucl/ClassLoaderDelegate";
				}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		clazz.accept(cw);
		byte[] newBytes = cw.toByteArray();
		
		Class<?> oldClass = Class.forName(name.replace('/', '.'));
		classLoader.createClass(name, newBytes, oldClass.getProtectionDomain());
	}
}
