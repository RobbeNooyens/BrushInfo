package me.robnoo02.brushinfo;

import java.util.ArrayList;

public class ConfigManager {

	private Main plugin;

	public ConfigManager() {
		this.plugin = Main.getInstance();
	}
	
	public static ConfigManager get() {
		return new ConfigManager();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getLoreStructure(){
		return (ArrayList<String>) plugin.getConfig().getList("lore");
	}
	
	public String getItemName() {
		return plugin.getConfig().getString("itemname");
	}
	
	public String getLoreLine(String s) {
		for(String str: getLoreStructure()) {
			if(str.toLowerCase().contains(s)) {
				return str;
			}
		}
		return null;
	}
	
	public String getConfigReloaded() {
		return ToolUtil.toColor(plugin.getConfig().getString("config-reloaded"));
	}
	
	public boolean enchantEnabled() {
		return plugin.getConfig().getBoolean("enchant-effect");
	}
	
	public boolean hideFlagsEnabled() {
		return plugin.getConfig().getBoolean("hide-flags");
	}
	
	public boolean updateOnScroll() {
		return plugin.getConfig().getBoolean("update-scroll");
	}
}
