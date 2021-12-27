package me.sub.cRanks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.sub.cRanks.Main.Main;
import me.sub.cRanks.Utils.Color;
import me.sub.cRanks.Utils.ProfileManager;

public class ProfileLoaded implements Listener {
	
	Main plugin;
	
	public ProfileLoaded(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler (priority = EventPriority.HIGH)
	public void profileload(PlayerJoinEvent e) {
		try {
			e.getPlayer().sendMessage(Color.chat("&aYour profile has been loaded."));
			ProfileManager pfm = new ProfileManager(plugin);
			pfm.loadPermissions(e.getPlayer().getUniqueId());
		} catch (Exception e2) {
			e2.printStackTrace();
			e.getPlayer().kickPlayer(Color.chat("&cAn error occured while loading your profile. Please log in again."));
		}
	}

}
