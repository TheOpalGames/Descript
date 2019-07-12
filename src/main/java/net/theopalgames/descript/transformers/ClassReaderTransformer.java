package net.theopalgames.descript.transformers;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClassReaderTransformer {
	@SneakyThrows
	public byte[] getCrBytes() {
		InputStream in = ClassReader.class.getResourceAsStream("/org/objectweb/asm/ClassReader");
		
		byte[] bytes;
		try (BufferedInputStream bin = new BufferedInputStream(in)) {
			bytes = new byte[bin.available()];
			bin.read(bytes);
		}
		
		ClassNode clazz = new ClassNode();
		new ClassReader(bytes).accept(clazz, ClassReader.SKIP_FRAMES);
		
		for (MethodNode method : clazz.methods)
			if (method.name.equals("<init>") && method.desc.equals("([BIZ)V")) {
				// ClassReader.<init>([BIZ)
				// ALOAD 0
				// INVOKESPECIAL java/lang/Object <init>()
				
				AbstractInsnNode before = method.instructions.get(1);
				before = insertInsn(method.instructions, before, new VarInsnNode(Opcodes.BALOAD, 1));
				before = insertInsn(method.instructions, before, new MethodInsnNode(Opcodes.INVOKESTATIC, "net/theopalgames/descript/transformers/ClassReaderTransformer", "shouldReturn", "([B)Z", false));
				before = insertInsn(method.instructions, before, new InsnNode(Opcodes.ICONST_0));
				
				LabelNode afterReturn = new LabelNode();
				
				before = insertInsn(method.instructions, before, new JumpInsnNode(Opcodes.IFEQ, afterReturn));
				before = insertInsn(method.instructions, before, new InsnNode(Opcodes.RETURN));
				before = insertInsn(method.instructions, before, afterReturn);
			}
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		clazz.accept(cw);
		return cw.toByteArray();
	}
	
	private AbstractInsnNode insertInsn(InsnList list, AbstractInsnNode before, AbstractInsnNode insn) {
		list.insert(before, insn);
		return insn;
	}
	
//	public boolean shouldReturn(byte[] bytes) {
//		if (bytes.length != NonserializedClassReader.dummyInitBytes.length)
//			return false;
//		
//		for (int i = 0; i < bytes.length; i++)
//			if (bytes[i] != NonserializedClassReader.dummyInitBytes[i])
//				return false;
//		
//		return true;
//	}
}
