package me.robnoo02.brushinfo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("brushinfo").setExecutor(new Commands());
		getCommand("brushinfo").setTabCompleter(new Commands());
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		saveDefaultConfig();
	}

	public static Main getInstance() {
		return JavaPlugin.getPlugin(Main.class);
	}

	@SuppressWarnings("deprecation")
	public void resetInvOnDisable(Player p) {
		ItemStack[] contents = p.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if (ToolUtil.hasBrushInName(item))
				contents[i] = new ItemStack(item.getType(), item.getAmount());
		}
		p.getInventory().setContents(contents);
		p.updateInventory();
	}

	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers())
			resetInvOnDisable(p);
	}

}
