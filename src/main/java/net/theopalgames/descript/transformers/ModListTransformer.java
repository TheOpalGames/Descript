package net.theopalgames.descript.transformers;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.google.common.collect.Sets;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

public final class ModListTransformer implements ITransformer<MethodNode> {
	@Override
	public MethodNode transform(MethodNode input, ITransformerVotingContext context) {
		// ALOAD 0
		// INVOKESPECIAL java/lang/Object <init> ()V
		AbstractInsnNode head = input.instructions.get(1);
		
		AbstractInsnNode next = new VarInsnNode(Opcodes.ALOAD, 2);
		input.instructions.insert(head, next);
		head = next;
		
		next = new MethodInsnNode(Opcodes.INVOKESTATIC, "net/theopalgames/descript/transformers/ModListTransformer", "transformList", "(Ljava/util/List;)V", false);
		input.instructions.insert(head, next);
		head = next;
		
		return input;
	}
	
	@Override
	public TransformerVoteResult castVote(ITransformerVotingContext context) {
		return TransformerVoteResult.YES;
	}
	
	@Override
	public Set<Target> targets() {
		return Sets.newHashSet(Target.targetMethod("net/minecraftforge/fml/ModList", "<init>", "(Ljava/util/List;Ljava/util/List;)V"));
	}
	
	public static void transformList(List<ModInfo> list) {
		list.removeIf(info -> info.getModId().equals("descript-dummy"));
	}
}
