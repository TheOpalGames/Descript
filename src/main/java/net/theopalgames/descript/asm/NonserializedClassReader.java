package net.theopalgames.descript.asm;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.tree.ClassNode;

import net.theopalgames.descript.api.launchwrapper.IClassTransformer;
import net.theopalgames.descript.transformers.LwWrapperTransformer;

public final class NonserializedClassReader extends ClassReader {
	public static final byte[] dummyInitBytes = {0x79, 0x77, 0x33};
	static final Map<IClassTransformer,LwWrapperTransformer> wrapperMap = new HashMap<>();
	
	public static void registerTransformer(LwWrapperTransformer transformer) {
		wrapperMap.put(transformer.getTransformer(), transformer);
	}
	
	private final ClassNode clazz;
	
	public NonserializedClassReader(IClassTransformer transformer, byte[] classFile) {
		super(dummyInitBytes);
		clazz = wrapperMap.get(transformer).untransformedNode.get();
	}
	
	@Override
	public void accept(ClassVisitor cv, int parsingOptions) {
		if (parsingOptions != 0)
			cv = new ParsingClassVisitor(parsingOptions, cv);
		
		clazz.accept(cv);
	}
}
