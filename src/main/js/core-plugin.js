var MAIN_CLASSES = ["net.minecraft.client.main.Main", "net.minecraft.server.MinecraftServer", "net.minecraft.data.Main"];
var INIT_BYTECODE = []; // TODO

var run = false;

function initializeCoreMod() {
	print("Loading Descript...");
	
	return {
		descript_core: {
			target: {
				type: "CLASS",
				names: listMainClasses // sorry, no other main classes right now
			},
			transformer: transformIt
		}
	};
}

function listMainClasses() {
	return MAIN_CLASSES;
}

function transformIt(clazz) {
        if (run)
            return clazz;
	    
        run = true;
        
        print("Injecting Descript call...");
        
        var Opcodes = Java.type("org.objectweb.asm.Opcodes");
        var MethodNode = Java.type("org.objectweb.asm.tree.MethodNode");
        var LdcInsnNode = Java.type("org.objectweb.asm.tree.LdcInsnNode");
        var IntInsnNode = Java.type("org.objectweb.asm.tree.IntInsnNode");
        var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
        var InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
        var LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
        var TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode");
        var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
        var LineNumberNode = Java.type("org.objectweb.asm.tree.LineNumberNode");
        var FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
        
        var clinit = clazz.methods.stream().filter(staticInitFilter).findAny().orElse(null);
        
        if (clinit == null) {
                clinit = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_SYNTHETIC, "<clinit>", "()V", null, null);
                clazz.methods.add(clinit);
        }
        
        // 9213: byte[] bytes = new byte[LENGTH];
        var lbl = new LabelNode();
		var topInsn = new LineNumberNode(9213, lbl);
		clinit.instructions.insert(topInsn);
		topInsn = pushInstruction(clinit, topInsn, lbl);
        
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode(INIT_BYTECODE.length));
        topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.NEWARRAY, Opcodes.T_BYTE));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 1));
        
        // 9214
        topInsn = addLineNumber(clinit, topInsn, 9214);
        
        for (var i = 0; i < INIT_BYTECODE.length; i++) {
                // bytes[i] = BYTECODE[i];
                topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
                topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode(i));
                topInsn = pushInstruction(clinit, topInsn, new IntInsnNode(Opcodes.BIPUSH, INIT_BYTECODE[i]));
                topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.BASTORE));
        }
        
        // 9215: URLClassLoader classLoader = new URLClassLoader(new URL[0]);
        topInsn = addLineNumber(clinit, topInsn, 9215);
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/net/URLClassLoader"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/net/URL"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/net/URLClassLoader", "<init>", "([Ljava/net/URL;)V"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 2));
        
        // 9216: Class[] params = new Class[4];
        topInsn = addLineNumber(clinit, topInsn, 9216);
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_4));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Class"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 3));
        
        // 9217: params[0] = Class.forName("java.lang.String");
        topInsn = addLineNumber(clinit, topInsn, 9217);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("java.lang.String")); // Have to do it this way because JS doesn't let you access ClassLoaders
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9218: params[1] = bytes.getClass();
        topInsn = addLineNumber(clinit, topInsn, 9218);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9219: params[2] = Integer.TYPE;
        topInsn = addLineNumber(clinit, topInsn, 9219);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_2));
        topInsn = pushInstruction(clinit, topInsn, new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/Integer", "TYPE", "Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9220: params[3] = Integer.TYPE;
        topInsn = addLineNumber(clinit, topInsn, 9220);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_3));
        topInsn = pushInstruction(clinit, topInsn, new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/Integer", "TYPE", "Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9221: Method defineClass = Class.forName("java.lang.ClassLoader").getDeclaredMethod("defineClass", params);
        topInsn = addLineNumber(clinit, topInsn, 9221);
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("java.lang.ClassLoader"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;"));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("defineClass"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 3));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 4));
        
        // 9222: defineClass.setAccessible(true);
        topInsn = addLineNumber(clinit, topInsn, 9222);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "setAccessible", "(Z)V"));
        
        // 9223: Object[] args = new Object[4];
        topInsn = addLineNumber(clinit, topInsn, 9223);
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_4));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 5));
        
        // 9224: args[0] = "net.theopalgames.descript.DescriptInit";
        topInsn = addLineNumber(clinit, topInsn, 9224);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("net.theopalgames.descript.DescriptInit"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9225: args[1] = bytes;
        topInsn = addLineNumber(clinit, topInsn, 9225);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_1));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9226: args[2] = new Integer(0);
        topInsn = addLineNumber(clinit, topInsn, 9226);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_2));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/lang/Integer"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9227: args[3] = bytes.length;
        topInsn = addLineNumber(clinit, topInsn, 9227);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_3));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.NEW, "java/lang/Integer"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.DUP));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 1));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ARRAYLENGTH));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.AASTORE));
        
        // 9228: Class<?> clazz = (Class<?>) defineClass.invoke(classLoader, args);
        topInsn = addLineNumber(clinit, topInsn, 9228);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 4));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 2));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 5)); // yay pi (not anymore)
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Class"));
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ASTORE, 6));
        
        // clazz.getDeclaredMethod("javaEntry", new Class[0]).invoke(null, new Object[0]);
        topInsn = addLineNumber(clinit, topInsn, 9229);
        topInsn = pushInstruction(clinit, topInsn, new VarInsnNode(Opcodes.ALOAD, 6));
        topInsn = pushInstruction(clinit, topInsn, new LdcInsnNode("javaEntry"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Class"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getDeclaredMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ACONST_NULL));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.ICONST_0));
        topInsn = pushInstruction(clinit, topInsn, new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));
        topInsn = pushInstruction(clinit, topInsn, new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;"));
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.POP));
        
        // return;
        topInsn = pushInstruction(clinit, topInsn, new InsnNode(Opcodes.RETURN));
        
        return clazz;
}

function staticInitFilter(method) {
	return method.name == "<clinit>";
}

function pushInstruction(method, topInsn, insn) {
	method.instructions.insert(topInsn, insn);
	return insn;
}

function addLineNumber(method, topInsn, number) {
	var LineNumberNode = Java.type("org.objectweb.asm.tree.LineNumberNode");
    var LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
	
	var lbl = new LabelNode();
	topInsn = pushInstruction(method, topInsn, new LineNumberNode(number, lbl));
	topInsn = pushInstruction(method, topInsn, lbl);
	
	return lbl;
}
