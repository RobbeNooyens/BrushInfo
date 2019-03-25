package me.robnoo02.brushinfo;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.boydti.fawe.object.FawePlayer;

public class ToolUtil {

	public static boolean hasBrushInName(ItemStack item) {
		if (item == null)
			return false;
		if (item.getItemMeta() == null)
			return false;
		if (item.getItemMeta().getLocalizedName() == null)
			return false;
		if (item.getItemMeta().getLocalizedName().equals("Brush"))
			return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean isBrushTool(Player p, ItemStack item) {
		if (item == null)
			return false;
		if (item.getType().isBlock())
			return false;
		return (FawePlayer.wrap(p).getSession().getTool(item.getTypeId()) != null);
	}

	public static String replaceUnicode(String toReplace) {
		String output = toReplace;
		output = output.replaceAll("u003e", ">");
		output = output.replaceAll("u003c", "<");
		output = output.replaceAll("u0026", "&.");
		return output;
	}

	public static String replace(String original, String replaceThis, String replaceWithThis) {
		String replacedString = original;
		try {
			if (original.contains(replaceThis))
				replacedString = replacedString.replaceAll(replaceThis, replaceWithThis);
		} catch (IllegalArgumentException e) {
			replacedString = replacedString.replaceAll(replaceThis, "Custom");
		}
		return replacedString;
	}

	public static String toColor(String input) {
		String output = input;
		output = (ChatColor.translateAlternateColorCodes('&', output));
		return output;
	}

	public static String removeColor(String input) {
		String output = input;
		output = (ChatColor.stripColor(output));
		return output;
	}

	public static String toUpper(String input) {
		String output = input;
		output = output.substring(0, 1).toUpperCase() + output.substring(1);
		return output;
	}
}
