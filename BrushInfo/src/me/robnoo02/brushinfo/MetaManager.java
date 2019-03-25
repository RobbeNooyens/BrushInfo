package me.robnoo02.brushinfo;

import java.util.ArrayList;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MetaManager {

	public static ItemStack ChangeMeta(Player p, ItemStack item) {
		if (!ToolUtil.isBrushTool(p, item))
			return item;
		ItemStack brushItem = item;
		BrushInfoTool tool = BrushInfoTool.wrap(p, item);
		BrushMeta brushMeta = tool.getMetaInfo();
		ConfigManager config = ConfigManager.get();
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(replaceVars(brushMeta, config.getItemName()));
		ArrayList<String> oldLore = config.getLoreStructure();
		ArrayList<String> lore = new ArrayList<>();
		for (String str : oldLore) {
			if ((str.contains("%seccommand%") && brushMeta.getSecondaryCommand().equals("none"))
					|| (str.contains("%smask%") && brushMeta.getSourceMask().equals("none")))
				continue;
			lore.add(replaceVars(brushMeta, str));
		}
		itemMeta.setLore(lore);
		if (config.enchantEnabled()) {
			itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
			itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		if (config.hideFlagsEnabled())
			itemMeta.addItemFlags(ItemFlag.values());
		itemMeta.setLocalizedName("Brush");
		brushItem.setItemMeta(itemMeta);
		return brushItem;
	}

	private static String replaceVars(BrushMeta meta, String input) {
		String s = input;
		s = ToolUtil.replace(s, "%brush%", meta.getBrushName());
		s = ToolUtil.replace(s, "%size%", meta.getSize());
		s = ToolUtil.replace(s, "%command%", meta.getPrimaryCommand());
		s = ToolUtil.replace(s, "%seccommand%", meta.getSecondaryCommand());
		s = ToolUtil.replace(s, "%hollow%", String.valueOf(meta.getPrimaryCommand().contains("-h")));
		s = ToolUtil.replace(s, "%pattern%", meta.getPattern());
		s = ToolUtil.replace(s, "%mask%", meta.getMask());
		s = ToolUtil.replace(s, "%smask%", meta.getSourceMask());
		return ToolUtil.toColor(s);
	}
}
