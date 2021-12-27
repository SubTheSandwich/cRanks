package me.sub.cRanks.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.sub.cRanks.Files.Players;
import me.sub.cRanks.Files.Ranks;
import me.sub.cRanks.Main.Main;

public class ProfileManager {
	
	Main plugin;
	
	public ProfileManager(Main plugin) {
		this.plugin = plugin;
	}
	
	public void loadProfile(UUID p) {	
		System.out.println("UUID of " + p + " joined.");
		if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
			System.out.println("Loaded rank " + Players.get().getString(p.toString() + ".profile.rank") + " for " + p);	
		} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
			System.out.println("Loaded rank " + plugin.data.getRank(p) + " for " + p);	
		}
	}
	
	public void createProfile(UUID p) {
		
		if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
		
			ArrayList<String> perms = new ArrayList<String>();
			Players.get().set(p.toString() + ".profile.rank", Ranks.get().getString("ranks.default.name"));
			Players.get().set(p.toString() + ".profile.permissions", perms);
			Players.save();
			
		} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
			
			try {
				PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT `NAME` FROM ranks");
				
				ResultSet rs = ps.executeQuery();
				
				String defaultRank = "";
				
				ArrayList<String> ranks = new ArrayList<String>();
				
				while (rs.next()) {
					ranks.add(rs.getString("NAME"));
					if (plugin.data.isDefault(rs.getString("NAME"))) {
						defaultRank = rs.getString("NAME");
					}
				}
				
				plugin.data.createPlayer(p);
				plugin.data.setRank(p, defaultRank);
				
				
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public void loadPermissions(UUID p) {
		
		if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
		
			PermissionHandler ph = new PermissionHandler(plugin);
			
			Player u = Bukkit.getPlayer(p);
			
			ArrayList<String> permissions = new ArrayList<String>();
			permissions.addAll(Ranks.get().getStringList("ranks." + Players.get().getString(p.toString() + ".profile.rank") + ".permissions"));
			permissions.addAll(Players.get().getStringList(p.toString() + ".profile.permissions"));
	
			
			for (String perms : permissions) {
				ph.addPermission(u, perms);
			}
			
			
			
			if (Ranks.get().isConfigurationSection("ranks." + Ranks.get().getString("ranks." + Players.get().getString(p.toString() + ".profile.rank") + ".inheritence"))) {
				ArrayList<String> ranksList = new ArrayList<String>();
				ArrayList<String> permis = new ArrayList<String>();
				
				for (String f : Ranks.get().getStringList("ranks." + Ranks.get().getString("ranks." + Players.get().getString(p.toString() + ".profile.rank") + ".inheritence"))) {
					ranksList.add(f);
				}
				
				for (String rank : ranksList) {
					permis.addAll(Ranks.get().getStringList("ranks." + rank + ".permissions"));
				}
				
				for (String l : permis) {
					ph.addPermission(u, l);
				}
				
				//inheriperms.addAll(Ranks.get().getStringList("ranks." + Ranks.get().getString("ranks." + Players.get().getString(p.toString() + ".profile.rank") + ".inheritence") + ".permissions"));
				//for (String e : inheriperms) {
					///ph.addPermission(u, e);
				//}
			}
		} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
			
			PermissionHandler ph = new PermissionHandler(plugin);
			
			Player u = Bukkit.getPlayer(p);
			
			String rank = plugin.data.getRank(p);
			
			String pulledString = plugin.data.getPermissions(rank);
			
			if (pulledString != null) {
			
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> rankPermissions = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				for (String perm : rankPermissions) {
					ph.addPermission(u, perm);
				}
			}
			
			String pulledString2 = plugin.data.getPermissions(p);
			
			if (pulledString2 != null) {
			
				String ad12 = pulledString2.replace("[", "");
				String convert2 = ad12.replace("]", "");
				
				List<String> playerPermissions = new ArrayList<String>(Arrays.asList(convert2.split(", ")));
				
				for (String perm : playerPermissions) {
					ph.addPermission(u, perm);
				}
			}
			
			String inherit = plugin.data.getInheritance(rank);
			
			if (inherit != null) {
				
				String ad123 = inherit.replace("[", "");
				String convert23 = ad123.replace("]", "");
				
				List<String> inheritence = new ArrayList<String>(Arrays.asList(convert23.split(", ")));
				
				for (String inherits : inheritence) {
					
					for (String perm : plugin.data.getListPermissions(inherits)) {
						
						ph.addPermission(u, perm);
						
					}
					
				}
			}
			
			
		}
		

	}

}
