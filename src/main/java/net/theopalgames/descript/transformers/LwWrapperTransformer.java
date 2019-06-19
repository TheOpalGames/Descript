package net.theopalgames.descript.transformers;

import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import lombok.RequiredArgsConstructor;
import net.theopalgames.descript.EverythingSet;
import net.theopalgames.descript.api.launchwrapper.IClassTransformer;

@RequiredArgsConstructor
public final class LwWrapperTransformer implements ITransformer<ClassNode> {
	private static final byte[] dummyCode = new byte[] {0xc, 0x0, 0xf, 0xf, 0xe, 0xe};
	
	private final IClassTransformer transformer;
	private final boolean serialize;
	
	private final ThreadLocal<ClassNode> transformedNode = new ThreadLocal<>();
	
	@Override
	public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
		if (serialize) {
			ClassWriter cw = new ClassWriter(0);
			input.accept(cw);
			
			byte[] basicClass = cw.toByteArray();
			byte[] transformedClass = transformer.transform(input.name, basicClass);
			
			ClassNode out = new ClassNode();
			new ClassReader(transformedClass).accept(out, 0);
			return out;
		} else {
			transformer.transform(input.name, dummyCode);
			return transformedNode.get();
		}
	}
	
	public void setTransformedNode(ClassNode node) {
		transformedNode.set(node);
	}
	
	@Override
	public TransformerVoteResult castVote(ITransformerVotingContext context) {
		return TransformerVoteResult.YES;
	}
	
	@Override
	public Set<Target> targets() {
		return EverythingSet.create();
	}
}
