package me.sub.cRanks.Events;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.sub.cRanks.Files.Players;
import me.sub.cRanks.Files.Ranks;

public class SpigotExtension extends PlaceholderExpansion {

	@Override
	public String getAuthor() {
		return "Sub";
	}

	@Override
	public String getIdentifier() {
		return "cranks";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public boolean canRegister() {
		return true;
	}
	
	@Override
	public boolean persist() {
		return true;
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String params) {
		
		if (p == null) {
			return null;
		}
		
		if (params.equalsIgnoreCase("rank")) {
			return String.valueOf(Ranks.get().getString("ranks." + Players.get().getString(p.getUniqueId().toString() + ".profile.rank") + ".name" ));
		} else if (params.equalsIgnoreCase("color")) {
			return String.valueOf(Ranks.get().getString("ranks." + Players.get().getString(p.getUniqueId().toString() + ".profile.rank") + ".guicolor" ));
		}
		
		
		
		
		
		return null;
		
		
	}
	

}
