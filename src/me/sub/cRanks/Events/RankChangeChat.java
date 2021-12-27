package me.sub.cRanks.Events;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.sub.cRanks.Files.Players;
import me.sub.cRanks.Files.Ranks;
import me.sub.cRanks.Main.Main;
import me.sub.cRanks.Utils.Color;

public class RankChangeChat implements Listener {
	
	Main plugin;
	
 	public RankChangeChat(Main plugin) {
 		this.plugin = plugin;
 	}
 	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (plugin.getConfig().getBoolean("settings.changechat.enabled")) {
			
			
			if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
			
				if (Ranks.get().isConfigurationSection("ranks." + Players.get().getString(e.getPlayer().getUniqueId().toString() + ".profile.rank"))) {
					e.setFormat(Color.chat(Ranks.get().getString("ranks." + Players.get().getString(e.getPlayer().getUniqueId().toString() + ".profile.rank") + ".prefix") + e.getPlayer().getDisplayName() + "&r: " + e.getMessage()));
				} else {
					ArrayList<String> de_fault = new ArrayList<String>();
					de_fault.addAll(Ranks.get().getStringList("ranks.ranklist"));
					for (String de_inferno : de_fault) {
						if (Ranks.get().getBoolean("ranks." + de_inferno + ".default")) {
							Players.get().set(e.getPlayer().getUniqueId().toString() + ".profile.rank", de_inferno);
							ArrayList<String> list = new ArrayList<String>();
							Players.get().set(e.getPlayer().getUniqueId().toString() + ".profile.permissions", list);
							Players.save();
							e.setFormat(Color.chat(Ranks.get().getString("ranks." + Players.get().getString(e.getPlayer().getUniqueId().toString() + ".profile.rank") + ".prefix") + e.getPlayer().getDisplayName() + "&r: " + e.getMessage()));
						}
					}
				}
			} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
				
				if (plugin.data.exists(plugin.data.getRank(e.getPlayer().getUniqueId()))) {
					
					String rank = plugin.data.getRank(e.getPlayer().getUniqueId());
					
					e.setFormat(Color.chat(plugin.data.getPrefix(rank) + e.getPlayer().getDisplayName() + "&r: " + e.getMessage()));
					
				} else {
					
					try {
						
						PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT `NAME` FROM ranks");
						
						ArrayList<String> ranks = new ArrayList<String>();
						
						ResultSet rs = ps.executeQuery();
						while (rs.next()) {
							ranks.add(rs.getString("NAME"));
						}
						
						for (String name : ranks) {
							if (plugin.data.isDefault(name)) {
								plugin.data.setRank(e.getPlayer().getUniqueId(), name);
								
								String rank = plugin.data.getRank(e.getPlayer().getUniqueId());
								
								e.setFormat(Color.chat(plugin.data.getPrefix(rank) + e.getPlayer().getDisplayName() + "&r: " + e.getMessage()));
							}
						}
						
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					
				}
				
				
			}
		} else {
			e.setCancelled(false);
		}
	}

}
