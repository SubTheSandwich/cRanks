package me.sub.cRanks.Main;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
//import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

//import me.clip.placeholderapi.PlaceholderAPI;
//import me.clip.placeholderapi.PlaceholderHook;
import me.sub.cRanks.Commands.RankCommand;
import me.sub.cRanks.Events.LoadProfile;
import me.sub.cRanks.Events.ProfileLoaded;
import me.sub.cRanks.Events.RankChangeChat;
import me.sub.cRanks.Events.SpigotExtension;
import me.sub.cRanks.Files.Players;
import me.sub.cRanks.Files.Ranks;
import me.sub.cRanks.SQL.MySQL;
import me.sub.cRanks.SQL.SQLGetter;

public class Main extends JavaPlugin {
	
	
	
	public Multimap<Player, Player> playername = ArrayListMultimap.create();
	public MySQL SQL;
	public SQLGetter data;
	private static Main instance;


    public Main() {
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }
	
	@Override
	public void onEnable() {
		
		cnfgs();
		evnts();
		cmds();
		
		new SpigotExtension().register();
		
		
		
	}
	
	@Override
	public void onDisable() {
		
		SQL.disconnect();
		
	}
	
	private void cmds() {
		this.getCommand("rank").setExecutor(new RankCommand(this));
	}
	
	private void cnfgs() {
		
		
		
		
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		
		if (getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
		
			Players.setup();
			
			Players.get().options().copyDefaults(true);
			Players.save();
			
			Ranks.setup();
			
			
			if (!Ranks.get().isConfigurationSection("ranks.default")) {
				if (!getConfig().getBoolean("settings.setup")) {
					getConfig().set("settings.setup", true);
					saveConfig();
					reloadConfig();
					ArrayList<String> perms = new ArrayList<String>();
					ArrayList<String> list = new ArrayList<String>();
					ArrayList<String> inh = new ArrayList<String>();
					list.add("default");
					Ranks.get().set("ranks.ranklist", list);
					Ranks.get().set("ranks.default.name", "default");
					Ranks.get().set("ranks.default.default", true);
	
					Ranks.get().set("ranks.default.permissions", perms);
					Ranks.get().set("ranks.default.guicolor", "a");
					Ranks.get().set("ranks.default.prefix", "&a");
					Ranks.get().set("ranks.default.inheritence", inh);
					Ranks.save();
				}
			}
		} else if (getConfig().getString("settings.ConfigurationType").equals("SQL")) {
			
			
			this.SQL = new MySQL();
			this.data = new SQLGetter(this);
			
			try {
				SQL.connect();
			} catch (ClassNotFoundException | SQLException e) {
				//e.printStackTrace();
				Bukkit.getLogger().info("Database not connected. Plugin is disabled.");
				getServer().getPluginManager().disablePlugin(this);
			}
			
			if (SQL.isConnected()) {
				Bukkit.getLogger().info("Database is connected.");
				data.createTable();
				
			}
			
			if (!getConfig().getBoolean("settings.setup")) {
				getConfig().set("settings.setup", true);
				saveConfig();
				reloadConfig();
				
				data.createRank("default");
				data.setDefault("default", true);
			}
			
			
			

			
			
		} else {
			System.out.println("Please use 'FLAT_FILE' or 'SQL' file storage.");
			getServer().getPluginManager().disablePlugin(this);
		}

	}
	
	private void evnts() {
		
		PluginManager pm = this.getServer().getPluginManager();
		
		pm.registerEvents(new LoadProfile(this), this);
		pm.registerEvents(new ProfileLoaded(this), this);
		pm.registerEvents(new RankChangeChat(this), this);
	}
	
	/*
	 * @SuppressWarnings("deprecation") private void registerPlaceholders() {
	 * 
	 * 
	 * PlaceholderAPI.registerPlaceholderHook("cranks", new PlaceholderHook() {
	 * 
	 * 
	 * @Override public String onRequest(OfflinePlayer p, String params) {
	 * 
	 * if (p != null && p.isOnline()) { return onPlaceholderRequest(p.getPlayer(),
	 * params); } return null;
	 * 
	 * }
	 * 
	 * @Override public String onPlaceholderRequest(Player p, String params) {
	 * 
	 * if (p == null) { return null; }
	 * 
	 * if (params.equalsIgnoreCase("rank")) {
	 * 
	 * return Ranks.get().getString("rank." +
	 * Players.get().getString(p.getUniqueId().toString() + ".profile.rank") +
	 * ".name"); }
	 * 
	 * 
	 * return null; }
	 * 
	 * 
	 * });
	 * 
	 * 
	 * }
	 */
	
	
	
	

}
