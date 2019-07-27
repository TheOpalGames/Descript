package net.theopalgames.descript.transformers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.theopalgames.descript.init.UclClassLoader;

@UtilityClass
public class UclTransformer {
	public static final String OLD_NAME = "java/net/URLClassLoader";
	public static final String INTERFACE = "net/theopalgames/descript/init/IClassLoaderDelegate";
	
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
		clazz.interfaces.add(INTERFACE);
		
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
		
		List<MethodNode> toRemove = new ArrayList<>();
		
		for (MethodNode method : clazz.methods) {
			Type type = Type.getMethodType(method.desc);
			Type[] args = type.getArgumentTypes();
			
			for (int i = 0; i < args.length; i++) {
				if (args[i].getDescriptor().equals("Ljava/net/URLClassLoader;"))
					args[i] = Type.getType("Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;");
			}
			
			if (method.name.equals("defineClass")) {
				method.desc = "(Ljava/lang/String;L" + clPackage + "/Resource;Z)Ljava/lang/Object;";
				
				for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
					if (insn instanceof MethodInsnNode) {
						MethodInsnNode cast = (MethodInsnNode) insn;
						
						if (cast.owner.equals("java/security/SecureClassLoader") && cast.name.equals("defineClass")) {
							cast.setOpcode(Opcodes.INVOKESTATIC);
							cast.owner = "net/theopalgames/descript/transformers/UclTransformer";
							
							Type[] newArgs = new Type[args.length+1];
							newArgs[0] = Type.getType("Ljava/lang/Object;");
							System.arraycopy(args, 0, newArgs, 1, args.length);
							args = newArgs;
							
							cast.desc = Type.getMethodDescriptor(Type.getType("L" + INTERFACE + ";"), newArgs);
							
							method.instructions.insertBefore(cast, new VarInsnNode(Opcodes.ILOAD, 3));
							method.instructions.insert(cast, new InsnNode(Opcodes.POP2));
						}
					}
			} else if (method.name.equals("findClass")) {
//				System.out.println("Transforming findClass...");
				method.desc = "(Ljava/lang/String;Z)Ljava/lang/Object;";
				
				for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
					if (insn instanceof MethodInsnNode) {
						MethodInsnNode cast = (MethodInsnNode) insn;
						
						if (cast.owner.equals("java/net/URLClassLoader") && cast.name.equals("defineClass")) {
							cast.desc = "(Ljava/lang/String;L" + clPackage + "/Resource;Z)Ljava/lang/Object;";
							method.instructions.insertBefore(cast, new VarInsnNode(Opcodes.ILOAD, 2));
						}
					}
				
//				System.out.println("findClass desc: " + method.desc);
			} else if (method.name.equals("<init>")) {
				if (!method.desc.equals("([Ljava/net/URL;Ljava/lang/ClassLoader;)V"))
					toRemove.add(method);
			} else if (method.name.equals("newInstance")) {
				toRemove.add(method);
			} else if (method.name.equals("<clinit>")) {
				method.instructions.insert(new InsnNode(Opcodes.ACONST_NULL)); // When the dup instruction is called, this gets duped.
				
				for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext();) {
					AbstractInsnNode insn = iter.next();
					
					if (insn instanceof MethodInsnNode) {
						MethodInsnNode cast = (MethodInsnNode) insn;
						
						if (cast.getOpcode() == Opcodes.INVOKESTATIC && cast.owner.endsWith("/SharedSecrets") && cast.name.equals("setJavaNetAccess")) {
							cast.owner = "net/theopalgames/descript/transformers/UclTransformer";
							cast.desc = "(Ljava/lang/Object;Ljava/lang/Object;)V";
						} else if (cast.getOpcode() == Opcodes.INVOKESPECIAL && cast.owner.startsWith("java/net/URLClassLoader$") && cast.name.equals("<init>"))
							iter.remove();
					} else if (insn instanceof TypeInsnNode) {
						TypeInsnNode cast = (TypeInsnNode) insn;
						
						if (cast.getOpcode() == Opcodes.NEW && cast.desc.startsWith("java/net/URLClassLoader$"))
							iter.remove();
					}
				}
			} else if (method.name.equals("addURL")) {
				method.access &= ~Opcodes.ACC_PROTECTED;
				method.access |= Opcodes.ACC_PUBLIC;
			}
			
			for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode cast = (MethodInsnNode) insn;
					
					if (cast.owner.startsWith("java/net/URLClassLoader"))
						cast.owner = cast.owner.replace("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate");
					
					Type calleeType = Type.getMethodType(cast.desc);
					Type[] calleeArgs = calleeType.getArgumentTypes();
					
					for (int i = 0; i < calleeArgs.length; i++)
						if (calleeArgs[i].getDescriptor().equals("Ljava/net/URLClassLoader;"))
							calleeArgs[i] = Type.getType("Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;");
					
					Type newType = Type.getMethodType(calleeType.getReturnType(), calleeArgs);
					cast.desc = newType.getDescriptor();
				} else if (insn instanceof FieldInsnNode) {
					FieldInsnNode cast = (FieldInsnNode) insn;
					
					if (cast.owner.equals("java/net/URLClassLoader")) {
						cast.owner = "net/theopalgames/descript/ucl/ClassLoaderDelegate";
					}
				} else if (insn instanceof TypeInsnNode) {
					TypeInsnNode cast = (TypeInsnNode) insn;
					
					if (cast.desc.startsWith("java/net/URLClassLoader"))
						cast.desc = cast.desc.replaceAll("java/net/URLClassLoader", "net/theopalgames/descript/ucl/ClassLoaderDelegate");
				}
			
			Type ret = type.getReturnType();
			if (ret.getDescriptor().equals("Ljava/lang/ClassLoader;"))
				ret = Type.getType("Lnet/theopalgames/descript/ucl/ClassLoaderDelegate;");
			
			Type newType = Type.getMethodType(ret, args);
			method.desc = newType.getDescriptor();
		}
		
//		toRemove.stream().map(method -> method.name + method.desc).forEach(System.out::println);
		clazz.methods.removeAll(toRemove);
		
		MethodNode getBytes = new MethodNode(Opcodes.ACC_PUBLIC, "getBytes", "(Ljava/lang/String;)[B", null, null);
		getBytes.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		getBytes.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
		getBytes.instructions.add(new InsnNode(Opcodes.ICONST_0));
		getBytes.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/theopalgames/descript/ucl/ClassLoaderDelegate", "findClass", "(Ljava/lang/String;Z)Ljava/lang/Object;", false));
		getBytes.instructions.add(new TypeInsnNode(Opcodes.CHECKCAST, "[B"));
		getBytes.instructions.add(new InsnNode(Opcodes.ARETURN));
		clazz.methods.add(getBytes);
		
		MethodNode getCodeSource = new MethodNode(Opcodes.ACC_PUBLIC, "getCodeSource", "(Ljava/lang/String;)Ljava/security/CodeSource;", null, null);
		getCodeSource.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
		getCodeSource.instructions.add(new VarInsnNode(Opcodes.ALOAD, 1));
		getCodeSource.instructions.add(new InsnNode(Opcodes.ICONST_1));
		getCodeSource.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/theopalgames/descript/ucl/ClassLoaderDelegate", "findClass", "(Ljava/lang/String;Z)Ljava/lang/Object;", false));
		getCodeSource.instructions.add(new TypeInsnNode(Opcodes.CHECKCAST, "java/security/CodeSource"));
		getCodeSource.instructions.add(new InsnNode(Opcodes.ARETURN));
		clazz.methods.add(getCodeSource);
		
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
	
	public void setJavaNetAccess(Object ourNull, Object dup) { // Suck up the stack entries
		// NOOP
	}
}
