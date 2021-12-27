package me.sub.cRanks.Events;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import me.sub.cRanks.Files.Players;
import me.sub.cRanks.Main.Main;
import me.sub.cRanks.Utils.Color;
import me.sub.cRanks.Utils.ProfileManager;

public class LoadProfile implements Listener {
	
	Main plugin;
	
	public LoadProfile(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void load(AsyncPlayerPreLoginEvent e) {
			
			
		if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
			
			try {
				ProfileManager pfm = new ProfileManager(plugin);
				
				UUID p = e.getUniqueId();
				
				if (Players.get().isConfigurationSection(p.toString() + ".profile")) {
					pfm.loadProfile(p);
				} else {
					pfm.createProfile(p);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				e.disallow(Result.KICK_OTHER, Color.chat("&cAn error occured while loading your profile. Please reconnect."));
			}
			
		} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {

			try {
				ProfileManager pfm = new ProfileManager(plugin);
					
				UUID p = e.getUniqueId();
					
				if (plugin.data.exists(p)) {
					
					pfm.loadProfile(p);
				} else {
					pfm.createProfile(p);
				}
			} catch (Exception e2) {
					e2.printStackTrace();
					e.disallow(Result.KICK_OTHER, Color.chat("&cAn error occured while loading your profile. Please reconnect."));
				}	
		}
	}

}
