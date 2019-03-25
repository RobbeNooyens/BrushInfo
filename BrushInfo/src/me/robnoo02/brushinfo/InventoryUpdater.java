package me.robnoo02.brushinfo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUpdater {

	@SuppressWarnings("deprecation")
	public static void updateInventory(Player p) {
		if (p == null)
			return;
		ItemStack[] contents = p.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if (item != null && item.getType() != Material.AIR && !item.getType().isBlock()
					&& ToolUtil.isBrushTool(p, item))
				contents[i] = MetaManager.ChangeMeta(p, item);
		}
		p.getInventory().setContents(contents);
		p.updateInventory();
	}

	@SuppressWarnings("deprecation")
	public static void updateAndReset(Player p, ItemStack item) {
		if (p == null || item == null)
			return;
		ItemStack[] contents = p.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack invItem = contents[i];
			if (ToolUtil.hasBrushInName(invItem))
				contents[i] = new ItemStack(invItem.getType(), invItem.getAmount());
		}
		p.getInventory().setContents(contents);
		p.updateInventory();
	}

	@SuppressWarnings("deprecation")
	public static void resetOnJoin(Player p) {
		if (p == null)
			return;
		ItemStack[] contents = p.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack invItem = contents[i];
			try {
				if (ToolUtil.hasBrushInName(invItem))
					contents[i] = new ItemStack(invItem.getType(), invItem.getAmount());
			} catch (Exception ignore) {
			}
		}
		p.getInventory().setContents(contents);
		p.updateInventory();
	}
}
