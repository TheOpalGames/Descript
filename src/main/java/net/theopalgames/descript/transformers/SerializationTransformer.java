package net.theopalgames.descript.transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SerializationTransformer {
	private static final int[] ALLOWED_INSTRUCTIONS = {
		Opcodes.IASTORE,
		Opcodes.IRETURN,
		Opcodes.INVOKESPECIAL,
		Opcodes.INVOKEVIRTUAL
	};
	
	public Result transform(byte[] bytes) {
		ClassNode clazz = new ClassNode();
		new ClassReader(bytes).accept(clazz, ClassReader.EXPAND_FRAMES);
		
		if (!clazz.interfaces.contains("net/theopalgames/descript/api/launchwrapper/IClassTransformer"))
			return new Result(bytes, false);
		
		for (MethodNode method : clazz.methods)
			if (method.name.equals("transform") && method.desc.equals("(Ljava/lang/String;[B)[B")) {
				int stackSize = -1;
				int binaryCheck = -1;
				List<Integer> binaryVars = new ArrayList<>();
				binaryVars.add(2);
				boolean serialize = false;
				
				for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator()) {
					if (insn instanceof FrameNode) {
						FrameNode cast = (FrameNode) insn;
						stackSize = cast.stack.size();
						
						if (stackSize < binaryCheck)
							binaryCheck = -1;
						
						cast.stack = cast.stack.stream().map(s -> (s.equals("org/objectweb/asm/ClassWriter") ? "net/theopalgames/descript/asm/NonserializedClassWriter" : s)).collect(Collectors.toList());
					} else if (insn instanceof VarInsnNode) {
						VarInsnNode cast = (VarInsnNode) insn;
						
						if (cast.getOpcode() == Opcodes.IALOAD && binaryVars.contains(cast.var))
							binaryCheck = stackSize+1;
						else if (cast.getOpcode() == Opcodes.IASTORE) {
							if (stackSize == binaryCheck && binaryCheck != -1) {
								binaryVars.add(cast.var);
								binaryCheck = -1;
							} else
								binaryVars.remove(cast.var);
						}
					} else if (insn instanceof MethodInsnNode) {
						MethodInsnNode cast = (MethodInsnNode) insn;
						
						if (cast.getOpcode() == Opcodes.INVOKEVIRTUAL) {
							Type type = Type.getMethodType(cast.desc);
							
							if (stackSize-type.getArgumentTypes().length <= binaryCheck)
								serialize = true;
							
							if (cast.owner.equals("org/objectweb/asm/ClassWriter"))
								cast.owner = "net/theopalgames/descript/asm/NonserializedClassWriter";
						} else if (cast.getOpcode() == Opcodes.INVOKESPECIAL) {
							if (cast.owner.equals("org/objectweb/asm/ClassReader") && cast.name.equals("<init>"))
								cast.owner = "net/theopalgames/descript/asm/NonserializedClassReader";
							else if (cast.owner.equals("org/objectweb/asm/ClassWriter") && cast.name.equals("<init>"))
								cast.owner = "net/theopalgames/descript/asm/NonserializedClassWriter";
						}
					} else if (insn instanceof TypeInsnNode) {
						TypeInsnNode cast = (TypeInsnNode) insn;
						
						if (cast.getOpcode() == Opcodes.NEW) {
							if (cast.desc.equals("org/objectweb/asm/ClassReader"))
								cast.desc = "net/theopalgames/descript/asm/NonserializedClassReader";
							else if (cast.desc.equals("org/objectweb/asm/ClassWriter"))
								cast.desc = "net/theopalgames/descript/asm/NonserializedClassWriter";
						}
					}
					
					if (binaryCheck == -1)
						continue;
					
					boolean ok = true;
					
					for (int allowed : ALLOWED_INSTRUCTIONS)
						if (insn.getOpcode() == allowed)
							ok = false;
					
					if (!ok || serialize)
						return new Result(bytes, true);
				}
			}
		
		ClassWriter cw = new ClassWriter(0);
		clazz.accept(cw);
		return new Result(cw.toByteArray(), false);
	}
	
	@RequiredArgsConstructor
	public final class Result {
		public final byte[] transformed;
		public final boolean serialize;
	}
}
