package me.robnoo02.brushinfo;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class TextBlock {

	// BrushInfo variables
	private Main plugin;
	
	/**
	 * Constructor for TextBlock
	 * @param plugin
	 * @param p
	 * @param type
	 */
	public TextBlock(Player p, TextBlockType type) {
		this.plugin = Main.getInstance();
		if(type.equals(TextBlockType.INFO)) {
			sendList(p, infoMenu());
		} else if(type.equals(TextBlockType.HELP)) {
			sendList(p, helpMenu());
		}
	}
	
	public static enum TextBlockType {
		INFO, HELP;
	}
	
	/**
	 * Send more than 1 line of text to player
	 * @param p is Player to send text to
	 * @param text is list to send
	 */
	public void sendList(Player p, ArrayList<String> text) {
		for (int i = 0; i < text.size(); i++) {
			p.sendMessage(ToolUtil.toColor(text.get(i)));
		}
	}
	
	/**
	 * Sends when Player types /brushinfo info
	 * @return textblocklist info page
	 */
	public ArrayList<String> infoMenu() {
		ArrayList<String> text = new ArrayList<>();
		text.add("");
		text.add("&3=-=-=-=-=-=-=-=-> &bBrushInfo Info &3<-=-=-=-=-=-=-=-=");
		text.add("");
		text.add("&2Creator: &bRobnoo02");
		text.add("&2Architect: &bPie3");
		text.add("&2Version: &b" + plugin.getDescription().getVersion());
		text.add("&2Description: &b" + plugin.getDescription().getDescription());
		text.add("");
		text.add("&3=-=-=-=-=-=-=-=-> &bBrushInfo Info &3<-=-=-=-=-=-=-=-=");
		return text;
	}
	
	/**
	 * Sends when Player types /brushinfo help
	 * @return textblocklist help page
	 */
	public ArrayList<String> helpMenu() {
		ArrayList<String> text = new ArrayList<>();
		text.add("");
		text.add("&3=-=-=-=-=-=-=-=-> &bBrushInfo Help &3<-=-=-=-=-=-=-=-=");
		text.add("");
		text.add("&2/brushinfo reload: &breload config");
		text.add("&2/brushinfo info: &bshow info");
		text.add("&2/brushinfo help: &bshow this page");
		text.add("&2/brushinfo refresh: &brefresh the itemlore of your tools");
		text.add("");
		text.add("&3=-=-=-=-=-=-=-=-> &bBrushInfo Help &3<-=-=-=-=-=-=-=-=");
		return text;
	}
	
}
