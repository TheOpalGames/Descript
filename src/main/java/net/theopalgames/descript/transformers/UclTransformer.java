package net.theopalgames.descript.transformers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.theopalgames.descript.UclClassLoader;

@UtilityClass
public class UclTransformer {
	@SneakyThrows
	public byte[] getUclLoader(UclClassLoader classLoader) {
		InputStream in = URLClassLoader.class.getResourceAsStream("/java/net/URLClassLoader.class");
		
		byte[] bytes;
		try (BufferedInputStream bin = new BufferedInputStream(in)) {
			bytes = new byte[bin.available()];
			bin.read(bytes);
		}
		
		ClassNode clazz = new ClassNode();
		new ClassReader(bytes).accept(clazz, ClassReader.SKIP_FRAMES);
		
		clazz.name = "net/theopalgames/descript/ucl/ClassLoaderDelegate";
		clazz.interfaces.add("net/theopalgames/descript/IClassLoaderDelegate");
		
		String clPackage = null;
		
		for (FieldNode field : clazz.fields)
			if (field.name.equals("ucp")) {
				Type type = Type.getType(field.desc);
				String[] parts = type.getClassName().split("/");
				
				String[] pkgParts = new String[parts.length-1];
				System.arraycopy(parts, 0, pkgParts, 0, pkgParts.length);
				clPackage = String.join("/", pkgParts);
			}
		
		if (clPackage == null)
			throw new IllegalStateException("What weird JDK are you using?");
		
		for (MethodNode method : clazz.methods) {
			if (method.name.equals("defineClass")) {
				method.desc = "(Ljava/lang/String;L" + clPackage + "/Resource;Z)Ljava/lang/Object;";
				
				for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
					if (insn instanceof MethodInsnNode) {
						MethodInsnNode cast = (MethodInsnNode) insn;
						
						if (cast.owner.equals("java/security/SecureClassLoader") && cast.name.equals("defineClass")) {
							cast.setOpcode(Opcodes.INVOKESTATIC);
							cast.owner = "net/theopalgames/descript/transformers/UclTransformer";
							
							Type type = Type.getMethodType(cast.desc);
							Type[] args = type.getArgumentTypes();
							
							Type[] newArgs = new Type[args.length+1];
							newArgs[0] = Type.getType("Ljava/lang/Object");
							System.arraycopy(args, 0, newArgs, 1, args.length);
							
							cast.desc = Type.getMethodDescriptor(Type.getType("Lnet/theopalgames/descript/IClassLoaderDelegate;"), newArgs);
							
							method.instructions.insertBefore(cast, new VarInsnNode(Opcodes.ILOAD, 3));
							method.instructions.insert(cast, new InsnNode(Opcodes.POP2));
						}
					}
			} else if (method.name.equals("findClass")) {
				method.desc = "(Ljava/lang/String;Z)Ljava/lang/Object";
				
				for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
					if (insn instanceof MethodInsnNode) {
						MethodInsnNode cast = (MethodInsnNode) insn;
						
						if (cast.owner.equals("java/net/URLClassLoader") && cast.name.equals("defineClass")) {
							cast.desc = "(Ljava/lang/String;L" + clPackage + "/Resource;Z)Ljava/lang/Object;";
							method.instructions.insertBefore(cast, new VarInsnNode(Opcodes.ILOAD, 2));
						}
					}
			}
			
			for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode cast = (MethodInsnNode) insn;
					
					if (cast.owner.equals("java/net/URLClassLoader"))
						cast.owner = "net/theopalgames/descript/ucl/ClassLoaderDelegate";
				}
		}
		
		for (InnerClassNode inner : clazz.innerClasses)
			InnerUclTransformer.loadBytes(inner.name, classLoader);
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		clazz.accept(cw);
		return cw.toByteArray();
	}
	
	public Object defineClass(String name, ByteBuffer bb, CodeSource source, boolean retSource) {
		if (retSource)
			return source;
		
		byte[] bytes = new byte[bb.remaining()];
		bb.get(bytes);
		return bytes;
	}
	
	public Object defineClass(String name, byte[] bytes, int offset, int length, CodeSource source, boolean retSource) {
		if (retSource)
			return source;
		
		byte[] newBytes = new byte[length];
		System.arraycopy(bytes, offset, newBytes, 0, length);
		return newBytes;
	}
}
