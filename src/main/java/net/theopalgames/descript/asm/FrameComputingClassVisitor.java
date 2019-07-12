package net.theopalgames.descript.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public final class FrameComputingClassVisitor extends ClassNode {
	private static final int[] LOAD = {
		Opcodes.ILOAD,
		Opcodes.LLOAD,
		Opcodes.FLOAD,
		Opcodes.DLOAD,
		Opcodes.ALOAD
	};
	
	private static final int[] STORE = {
		Opcodes.ISTORE,
		Opcodes.LSTORE,
		Opcodes.FSTORE,
		Opcodes.DSTORE,
		Opcodes.ASTORE
	};
	
	private static final int[] ARRAY_LOAD = {
		Opcodes.IALOAD,
		Opcodes.LALOAD,
		Opcodes.FALOAD,
		Opcodes.DALOAD,
		Opcodes.AALOAD,
		Opcodes.BALOAD,
		Opcodes.CALOAD,
		Opcodes.SALOAD
	};
	
	private static final int[] ARRAY_STORE = {
		Opcodes.IASTORE,
		Opcodes.LASTORE,
		Opcodes.FASTORE,
		Opcodes.DASTORE,
		Opcodes.AASTORE,
		Opcodes.BASTORE,
		Opcodes.CASTORE,
		Opcodes.SASTORE
	};
	
	private static final int[] INT_CONSTANT = {
		Opcodes.ICONST_M1,
		Opcodes.ICONST_0,
		Opcodes.ICONST_1,
		Opcodes.ICONST_2,
		Opcodes.ICONST_3,
		Opcodes.ICONST_4,
		Opcodes.ICONST_5,
		Opcodes.BIPUSH,
		Opcodes.SIPUSH
	};
	
	private static final int[] LONG_CONSTANT = {
		Opcodes.LCONST_0,
		Opcodes.LCONST_1
	};
	
	private static final int[] FLOAT_CONSTANT = {
		Opcodes.FCONST_0,
		Opcodes.FCONST_1,
		Opcodes.FCONST_2
	};
	
	private static final int[] DOUBLE_CONSTANT = {
		Opcodes.DCONST_0,
		Opcodes.DCONST_1
	};
	
	private static final int[] BIOPERAND = {
		Opcodes.IADD,
		Opcodes.ISUB,
		Opcodes.IMUL,
		Opcodes.IDIV,
		Opcodes.IREM,
		Opcodes.IAND,
		Opcodes.IOR,
		Opcodes.IXOR,
		Opcodes.ISHL,
		Opcodes.ISHR,
		Opcodes.IUSHR,
		
		Opcodes.LADD,
		Opcodes.LSUB,
		Opcodes.LMUL,
		Opcodes.LDIV,
		Opcodes.LREM,
		Opcodes.LAND,
		Opcodes.LOR,
		Opcodes.LXOR,
		Opcodes.LSHL,
		Opcodes.LSHR,
		Opcodes.LUSHR,
		
		Opcodes.FADD,
		Opcodes.FSUB,
		Opcodes.FMUL,
		Opcodes.FDIV,
		Opcodes.FREM,
		
		Opcodes.DADD,
		Opcodes.DSUB,
		Opcodes.DMUL,
		Opcodes.DDIV,
		Opcodes.DREM,
	};
	
	private static final int[] INT_CAST = {
		Opcodes.L2I,
		Opcodes.F2I,
		Opcodes.D2I
	};
	
	private static final int[] LONG_CAST = {
		Opcodes.I2L,
		Opcodes.F2L,
		Opcodes.D2L
	};
	
	private static final int[] FLOAT_CAST = {
		Opcodes.I2F,
		Opcodes.L2F,
		Opcodes.D2F
	};
	
	private static final int[] DOUBLE_CAST = {
		Opcodes.I2D,
		Opcodes.L2D,
		Opcodes.F2D
	};
	
	
	private final ClassVisitor cv;
	private Frame frame;
	
	public FrameComputingClassVisitor(ClassVisitor cv) {
		this.cv = cv;
	}
	
	@Override
	public void visitEnd() {
		super.visitEnd();
		
		for (MethodNode method : methods)
			for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator()) {
				if (contains(LOAD, insn.getOpcode())) {
					VarInsnNode cast = (VarInsnNode) insn;
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, frame.local[cast.var])); // #1
				} else if (contains(STORE, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(-1)); // #2
					addFrame(method.instructions, insn, DiffFrame.APPEND(frame.lastStack())); // #1
				}
				
				else if (contains(ARRAY_LOAD, insn.getOpcode())) {
					String arrayType = (String) frame.stack[frame.nStack-1];
					Type parsed = Type.getType(arrayType);
					Type component = parsed.getElementType();
					Object frameComponent = ParsingMethodVisitor.toFrameType(component);
					
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, frameComponent)); // #2
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(-1)); // #1
				} else if (contains(ARRAY_STORE, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(-2));
				}
				
				else if (insn.getOpcode() == Opcodes.ACONST_NULL) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, Opcodes.NULL));
				} else if (contains(INT_CONSTANT, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, Opcodes.INTEGER));
				} else if (contains(LONG_CONSTANT, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, Opcodes.LONG));
				} else if (contains(FLOAT_CONSTANT, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, Opcodes.FLOAT));
				} else if (contains(DOUBLE_CONSTANT, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, Opcodes.DOUBLE));
				}
				
				else if (insn.getOpcode() == Opcodes.POP) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(-1));
				} else if (insn.getOpcode() == Opcodes.POP2) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(-2));
				}
				
				else if (insn.getOpcode() == Opcodes.DUP) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(1, frame.lastStack()));
				} else if (insn.getOpcode() == Opcodes.DUP_X1) {
					addFrame(method.instructions, insn, DiffFrame.INSERT_STACK(-2, frame.lastStack()));
				} else if (insn.getOpcode() == Opcodes.DUP_X2) {
					addFrame(method.instructions, insn, DiffFrame.INSERT_STACK(-3, frame.lastStack()));
				} else if (insn.getOpcode() == Opcodes.DUP2) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(2, frame.lastTwoStack()));
				} else if (insn.getOpcode() == Opcodes.DUP2_X1) {
					addFrame(method.instructions, insn, DiffFrame.INSERT_STACK(-3, frame.lastTwoStack()));
				} else if (insn.getOpcode() == Opcodes.DUP2_X2) {
					addFrame(method.instructions, insn, DiffFrame.INSERT_STACK(-4, frame.lastTwoStack()));
				}
				
				else if (insn.getOpcode() == Opcodes.SWAP) {
					Object[] newStack = new Object[frame.stack.length];
					System.arraycopy(frame.stack, 0, newStack, 0, newStack.length-2);
					
					newStack[newStack.length-2] = frame.stack[newStack.length-1];
					newStack[newStack.length-1] = frame.stack[newStack.length-2];
					
					frame = new Frame(frame.nLocal, frame.local, newStack.length, newStack);
					method.instructions.insert(insn, frame.toNode());
				}
				
				else if (contains(BIOPERAND, insn.getOpcode())) {
					addFrame(method.instructions, insn, DiffFrame.APPEND_STACK(-1));
				}
				
				else if (contains(INT_CAST, insn.getOpcode())) {
					addFrame(method.instructions, insn, null);
				}
			}
	}
	
	private boolean contains(int[] array, int opcode) {
		for (int x : array)
			if (x == opcode)
				return true;
		
		return false;
	}
	
	private void addFrame(InsnList list, AbstractInsnNode insn, DiffFrame diff) {
		frame = getByteFrame(diff);
		list.insert(insn, frame.toNode());
	}
	
	private Frame getByteFrame(DiffFrame diff) {
		if (version < Opcodes.V1_6)
			return frame.apply(diff);
		
		return diff;
	}
}
