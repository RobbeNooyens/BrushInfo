package me.robnoo02.brushinfo;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

public class Commands implements CommandExecutor, TabCompleter {

	private Main plugin;

	public Commands() {
		this.plugin = Main.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			String mainCmd = cmd.getName();
			if (mainCmd.equalsIgnoreCase("brushinfo")) {
				if (args.length == 0) {
					new TextBlock(p, TextBlock.TextBlockType.HELP);
					return true;
				}
				if (args.length > 0) {
					String subCmd = args[0];
					if (subCmd.equalsIgnoreCase("reload")) {
						if (p.hasPermission("brushinfo.reload")) {
							plugin.reloadConfig();
							ConfigManager config = new ConfigManager();
							p.sendMessage(config.getConfigReloaded());
						}
					} else if (subCmd.equalsIgnoreCase("info")) {
						new TextBlock(p, TextBlock.TextBlockType.INFO);
					} else if (subCmd.equalsIgnoreCase("help")) {
						new TextBlock(p, TextBlock.TextBlockType.HELP);
					} else if (subCmd.equalsIgnoreCase("refresh")) {
						if (p.hasPermission("brushinfo.refresh")) {
							InventoryUpdater.resetOnJoin(p);
						}
					} else {
						new TextBlock(p, TextBlock.TextBlockType.HELP);
					}
				}
			}

		}
		return true;
	}

	/**
	 * BrushInfo tabcompleter
	 */
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> subCmd = Arrays.asList("reload", "info", "help", "refresh");
		List<String> fList = Lists.newArrayList();
		if (args.length == 1) {
			for (String s : subCmd)
				if (s.toLowerCase().startsWith(args[0].toLowerCase()))
					fList.add(s);
			return fList;
		} else if (args.length > 1) {
			return fList;
		}
		return null;
	}

}
