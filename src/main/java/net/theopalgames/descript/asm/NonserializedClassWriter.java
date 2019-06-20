package net.theopalgames.descript.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import net.theopalgames.descript.api.launchwrapper.IClassTransformer;

public final class NonserializedClassWriter extends ClassVisitor {
	public static final byte[] dummyOutput = {0x69, 0x55, 0x21};
	
	private final ThreadLocal<ClassNode> local;
	private final ClassNode clazz;
	
	public NonserializedClassWriter(int flags, IClassTransformer transformer) {
		this(null, flags, transformer);
	}
	
	public NonserializedClassWriter(ClassReader cr, int flags, IClassTransformer transformer) {
		this(cr, flags, transformer, new ClassNode());
	}
	
	private NonserializedClassWriter(ClassReader cr, int flags, IClassTransformer transformer, ClassNode clazz) {
		super(Opcodes.ASM6, getDelegateVisitor(clazz, flags));
		
		this.local = NonserializedClassReader.wrapperMap.get(transformer).transformedNode;
		this.clazz = clazz;
	}
	
	private static ClassVisitor getDelegateVisitor(ClassNode clazz, int flags) {
		if (flags == 0)
			return clazz;
		
		return new ComputingClassVisitor(flags, clazz);
	}
	
	public byte[] toByteArray() {
		local.set(clazz);
		return dummyOutput;
	}
}
