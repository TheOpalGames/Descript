package net.theopalgames.descript.api;

import javax.annotation.Nullable;

import net.minecraftforge.forgespi.language.IModInfo;

public interface IDescriptModInfo extends IModInfo {
	@Nullable
	public abstract String getMain();
}
