package net.theopalgames.descript.transformers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import lombok.experimental.UtilityClass;
import net.theopalgames.descript.init.UclClassLoader;

@UtilityClass
public class InnerUclTransformer {
	@SuppressWarnings("unchecked")
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
		new ClassReader(bytes).accept(clazz, 0);
		
		clazz.name = clazz.name.replace("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate");
		
		for (FieldNode field : clazz.fields) {
			if (field.desc.equals("Ljava/net/URLClassLoader;"))
				field.desc = "Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;";
		}
		
		for (MethodNode method : clazz.methods) {
			Type type = Type.getMethodType(method.desc);
			Type[] args = type.getArgumentTypes();
			
			for (int i = 0; i < args.length; i++) {
				if (args[i].getDescriptor().equals("Ljava/net/URLClassLoader;"))
					args[i] = Type.getType("Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;");
			}
			
//			System.out.println("\n" + clazz.name + "" + method.name + method.desc);
			
			for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator()) {
//				System.out.println(insn.getOpcode());
				
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode cast = (MethodInsnNode) insn;
					
					if (cast.owner.startsWith("java/net/URLClassLoader")) {
						cast.owner = cast.owner.replace("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate");
						
						if (cast.name.equals("defineClass"))
							cast.desc = "(Ljava/lang/String;L" + clPackage + "/Resource;)Lorg/apache/commons/lang3/tuple/Pair;";
					}
					
					Type calleeType = Type.getMethodType(cast.desc);
					Type[] calleeargs = calleeType.getArgumentTypes();
					
					for (int i = 0; i < calleeargs.length; i++) {
						if (calleeargs[i].getDescriptor().equals("Ljava/net/URLClassLoader;"))
							calleeargs[i] = Type.getType("Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;");
					}
					
					cast.desc = Type.getMethodType(calleeType.getReturnType(), calleeargs).getDescriptor();
					
					if (cast.getOpcode() == Opcodes.INVOKEVIRTUAL)
						System.out.println(clazz.name + "/" + method.name + method.desc + " -> " + cast.owner + "/" + cast.name + cast.desc);
				} else if (insn instanceof FieldInsnNode) {
					FieldInsnNode cast = (FieldInsnNode) insn;
					
					if (cast.owner.startsWith("java/net/URLClassLoader"))
						cast.owner = cast.owner.replace("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate");
					
					if (cast.desc.equals("Ljava/net/URLClassLoader;"))
						cast.desc = "Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;";
				} else if (insn instanceof FrameNode) {
					FrameNode cast = (FrameNode) insn;
					
					for (List<?> list : new List[] {cast.stack, cast.local})
						if (list != null)
							for (int i = 0; i < list.size(); i++) {
								Object element = list.get(i);
								
								if (element instanceof String && ((String) element).startsWith("java/net/URLClassLoader"))
									((List<Object>) list).set(i, ((String) element).replace("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate"));
							}
								
				}
			}
			
			Type newType = Type.getMethodType(type.getReturnType(), args);
			method.desc = newType.getDescriptor();
		}
		
		ClassWriter cw = new ClassWriter(0);
		clazz.accept(cw);
		byte[] newBytes = cw.toByteArray();
		
		Class<?> oldClass = Class.forName(name.replace('/', '.'));
		classLoader.createClass(clazz.name.replace('/', '.'), newBytes, oldClass.getProtectionDomain());
//		newClass.getName(); // Make sure it fully initializes
	}
}
