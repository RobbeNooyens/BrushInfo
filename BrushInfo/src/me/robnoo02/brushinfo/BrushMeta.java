package me.robnoo02.brushinfo;

import java.util.Arrays;
import java.util.List;

import com.sk89q.worldedit.command.tool.BrushTool;

public class BrushMeta {

	private BrushTool tool;
	private String[] toolString;

	private String primaryCommand = "none";
	private String secondaryCommand = "none";
	private String mask = "none";
	private String sourceMask = "none";
	private String brushName = "none";
	private String size = "0";
	private String pattern = "none";

	public BrushMeta(BrushTool tool) {
		this.tool = tool;
		this.toolString = tool.toString().split("\"");
		this.setInformation();
	}

	public String getPrimaryCommand() {
		return this.primaryCommand;
	}

	public String getSecondaryCommand() {
		return this.secondaryCommand;
	}

	public String getBrushName() {
		return brushName;
	}

	public String getMask() {
		return mask;
	}

	public String getSourceMask() {
		return sourceMask;
	}

	public BrushTool getBrushTool() {
		return tool;
	}

	public String getSize() {
		return size;
	}

	public String getPattern() {
		return pattern;
	}

	/***************************
	 * 
	 * Algoritms to grab information from toolString
	 * vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	 * 
	 ***************************/

	public void setInformation() {
		boolean secondary = false;
		for (int i = 0; i < toolString.length; i++) {
			if (toolString.length >= i + 2) {
				switch (toolString[i]) {
				case "secondary":
					secondary = true;
					break;
				case "primary":
					secondary = false;
					break;
				case "BRUSH":
					if (secondary)
						this.secondaryCommand = toolString[i + 2];
					else
						this.primaryCommand = toolString[i + 2];
					break;
				case "MASK":
					mask = ToolUtil.replaceUnicode(toolString[i + 2]);
					break;
				case "SOURCE_MASK":
					sourceMask = ToolUtil.replaceUnicode(toolString[i + 2]);
					break;
				}
			}
		}
		if (primaryCommand.contains(" "))
			this.brushName = primaryCommand.substring(0, primaryCommand.indexOf(' '));
		else
			this.brushName = primaryCommand;
		this.brushName = brushName.substring(0, 1).toUpperCase() + brushName.substring(1); // Uppercase
		this.size = String.valueOf((int) tool.getSize());
		setPattern();
	}

	public void setPattern() {
		String command = getPrimaryCommand();
		String flags = "plangbtfrh";
		for (int i = 0; i < flags.length(); i++) {
			char c = flags.charAt(i);
			command = command.replaceAll("-" + c + " ", "");
		}

		List<String> setNone = Arrays.asList("erode", "pull", "sweep", "blendball", "ex", "smooth", "gravity",
				"clipboard", "butcher", "command", "crop", "copypaste");
		List<String> increment1 = Arrays.asList("height", "populateschematic", "flatten", "cliff", "layer", "image");
		List<String> increment3 = Arrays.asList("smcd");

		int place = 1;
		if (setNone.contains(brushName.toLowerCase())) {
			return;
		} else if (increment1.contains(brushName.toLowerCase())) {
			place += 1;
		} else if (increment3.contains(brushName.toLowerCase())) {
			place += 3;
		}
		String[] args = command.split(" ");
		if (place > 0 && place < args.length) {
			this.pattern = args[place];
		}
	}
}
