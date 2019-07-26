package net.theopalgames.descript.transformers;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.google.common.collect.Sets;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import net.minecraftforge.fml.ModContainer;
import net.theopalgames.descript.CoreModLoader;

public final class ModLoaderTransformer implements ITransformer<MethodNode> {
	@Override
	public MethodNode transform(MethodNode method, ITransformerVotingContext context) {
//		System.out.println("Descript transforming ModLoader...");
		
		InsnNode ret = null;
		
		for (AbstractInsnNode insn : (Iterable<AbstractInsnNode>) () -> method.instructions.iterator())
			if (insn.getOpcode() == Opcodes.ARETURN)
				ret = (InsnNode) insn;
		
		method.instructions.insertBefore(ret, new MethodInsnNode(Opcodes.INVOKESTATIC, "net/theopalgames/descript/transformers/ModLoaderTransformer", "transformModList", "(Ljava/util/List;)Ljava/util/List;", false));
		return method;
	}
	
	@Override
	public TransformerVoteResult castVote(ITransformerVotingContext context) { // why is there even a context being passed it has no functions
		return TransformerVoteResult.YES;
	}
	
	@Override
	public Set<Target> targets() {
		// TODO: Again, stop using google
		return Sets.newHashSet(Target.targetMethod("net.minecraftforge.fml.ModLoader", "buildMods", "(Lnet/minecraftforge/fml/loading/moddiscovery/ModFile;Lcpw/mods/modlauncher/TransformingClassLoader;)Ljava/util/List;"));
	}
	
	public static List<ModContainer> transformModList(List<ModContainer> theList) {
//		System.out.print("Removing descript dummy mod...");
		
		theList.removeIf(container -> container.getModId().equals("descript-dummy"));
		theList.addAll(CoreModLoader.getContainers());
		
		return theList;
	}
}
