const MAIN_CLASSES = ["net.minecraft.client.main.Main", "net.minecraft.server.MinecraftServer", "net.minecraft.data.Main"];
const INIT_BYTECODE = []; // TODO

var run = false;

function initializeCoreMod() {
	return {
		"descript-core": {
			"target": {
				"type": "CLASS",
				"names": MAIN_CLASSES // Please don't use a different main class, maybe that will be supported in the future, who knows...
			},
			"transformer": function(clazz) {
				var Opcodes = Java.type("org.objectweb.asm.Opcodes");
				var MethodNode = Java.type("org.objectweb.asm.tree.MethodNode");
				var LdcInsnNode = Java.type("org.objectweb.asm.tree.LdcInsnNode");
				var IntInsnNode = Java.type("org.objectweb.asm.tree.IntInsnNode");
				var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
				var InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
				var TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode");
				var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
				
				var clinit = clazz.methods.filter(method => (method.name == "<clinit>")).findAny().orElse(null);
				
				if (clinit == null) {
					clinit = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, "<clinit>");
					clazz.methods.add(clinit);
				}
				
				var topInsn = new LdcInsnNode(INIT_BYTECODE.length);
				clinit.instructions.insert(topInsn);
				
				topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.NEW_ARRAY, Opcodes.T_BYTE));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 0));
				
				for (var i = 0; i < INIT_BYTECODE.length; i++) {
					topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 0));
					topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode(i));
					topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.BIPUSH, INIT_BYTECODE[i]));
					topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.BASTORE));
				}
				
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/net/URLClassLoader"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/net/URL"));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/net/URLClassLoader", "<init>", "([Ljava/net/URL;)V"));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 1));
				
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_4));
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Class"));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 2));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
				topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("java.lang.String")); // Have to do it this way because JS doesn't let you access ClassLoaders
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 0));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_2));
				topInsn = pushInstruction(clinit, topInsn, new FieldInsnNode(Opcodes.GETFIELD, "java/lang/Integer", "TYPE", "Ljava/lang/Class;"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_3));
				topInsn = pushInstruction(clinit, topInsn, new FieldInsnNode(Opcodes.GETFIELD, "java/lang/Integer", "TYPE", "Ljava/lang/Class;"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("java.lang.ClassLoader"));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
				topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("defineClass"));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 3));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "setAccessible", "(Z)V"));
				
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_4));
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 4));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
				topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("net.theopalgames.descript.DescriptInit"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 0));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_2));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_3));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 0));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ARRAYLENGTH));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4)); // yay pi
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Class"));
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 5));
				
				topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
				topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("javaEntry"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Class"));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ACONST_NULL));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
				topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
				topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.POP));
				
				topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.RETURN));
			}
		}
	};
}

function pushInstruction(method, topInsn, insn) {
	method.instructions.insertAfter(topInsn, insn);
	return insn;
}