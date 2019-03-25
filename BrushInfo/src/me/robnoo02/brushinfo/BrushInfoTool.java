package me.robnoo02.brushinfo;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.command.tool.BrushTool;
import com.sk89q.worldedit.command.tool.InvalidToolBindException;

public class BrushInfoTool {
	
	private BrushTool tool;
	private BrushMeta meta;

	@SuppressWarnings("deprecation")
	private BrushInfoTool(Player p, ItemStack item) {
		try {
			this.tool = FawePlayer.wrap(p).getSession().getBrushTool(item.getTypeId());
		} catch (InvalidToolBindException e) {
		}
		this.meta = (tool != null ? new BrushMeta(tool) : null);
	}
	
	public BrushMeta getMetaInfo() {
		return meta;
	}

	public static BrushInfoTool wrap(Player p, ItemStack item) {
		return (ToolUtil.isBrushTool(p, item) ? new BrushInfoTool(p, item) : null);
	}
}
