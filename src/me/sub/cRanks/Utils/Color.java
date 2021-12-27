package me.sub.cRanks.Utils;

import org.bukkit.ChatColor;

public class Color {
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	
	public static String strip(String s) {
		return ChatColor.stripColor(s);
	}
}
