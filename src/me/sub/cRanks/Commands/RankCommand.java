package me.sub.cRanks.Commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//import me.clip.placeholderapi.PlaceholderAPI;
import me.sub.cRanks.Files.Players;
import me.sub.cRanks.Files.Ranks;
import me.sub.cRanks.Main.Main;
import me.sub.cRanks.Utils.Color;
import me.sub.cRanks.Utils.PermissionHandler;


public class RankCommand implements CommandExecutor {
	
	Main plugin;
	
	public RankCommand(Main plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		if (cmd.getName().equalsIgnoreCase("rank")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
					if (args.length == 0) {
						p.sendMessage(Color.chat("&cUsage: /rank help"));
					} else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("help")) {
							
							//p.sendMessage(Color.chat("&" + PlaceholderAPI.setPlaceholders(p, "%cranks_color%") + PlaceholderAPI.setPlaceholders(p, "%cranks_rank%")));
							
							p.sendMessage(Color.chat("                             &bcRanks Help"));
							p.sendMessage(Color.chat("&7&m-----------------------------------------------"));
							p.sendMessage(Color.chat("&c/rank create <rank>&7: &rCreates the specified rank."));
							p.sendMessage(Color.chat("&c/rank remove <rank>&7: &rRemoves the specified rank."));
							p.sendMessage(Color.chat("&c/rank addperm <player/rank> <permission>&7: &rAdd a permission to a player/rank."));
							p.sendMessage(Color.chat("&c/rank removeperm <player/rank> <permission>&7: &rRemove a permission to a player/rank."));
							p.sendMessage(Color.chat("&c/rank set <player> <rank>&7: &rSets the specified player's rank to the specified rank."));
							p.sendMessage(Color.chat("&c/rank setprefix <rank> <prefix>&7: &rSets the rank's prefix to the specified prefix."));
							p.sendMessage(Color.chat("&c/rank list&7: &rShows a list of all the ranks."));
							p.sendMessage(Color.chat("&c/rank addinheritance <rank> <rank>&7: &rAllows the specified rank to recieve permissions from the other rank."));
							p.sendMessage(Color.chat("&c/rank removeinheritance <rank> <rank>&7: &rRemoves the rank's inheritence."));
							p.sendMessage(Color.chat("&c/rank setdefault <rank>&7: &rSets the specified rank to the default rank."));
							p.sendMessage(Color.chat("&c/rank setguicolor <rank> <color>&7: &rSet the text and wool color a rank displays in commands. &7(Can be used for scoreboard coloring)"));
							p.sendMessage(Color.chat("&c/rank info <player/rank>&7: &rReturns information about a player or a specified rank."));
							p.sendMessage(Color.chat("&7&m-----------------------------------------------"));
						} else if (args[0].equalsIgnoreCase("set")) {
							p.sendMessage(Color.chat("&cUsage: /rank set <player> <rank>"));
						} else if (args[0].equalsIgnoreCase("removeperm") || args[0].equalsIgnoreCase("removepermission")) {
							p.sendMessage(Color.chat("&cUsage: /rank removeperm <player/rank> <permission>"));
						} else if (args[0].equalsIgnoreCase("addperm") || args[0].equalsIgnoreCase("addpermission")) {
							p.sendMessage(Color.chat("&cUsage: /rank addperm <player/rank> <permission>"));
						} else if (args[0].equalsIgnoreCase("set")) {
							p.sendMessage(Color.chat("&cUsage: /rank set <player> <rank>"));
						} else if (args[0].equalsIgnoreCase("setprefix")) {
							p.sendMessage(Color.chat("&cUsage: /rank setprefix <rank> <prefix>"));
						} else if (args[0].equalsIgnoreCase("remove")) {
							p.sendMessage(Color.chat("&cUsage: /rank remove <rank>"));;
						} else if (args[0].equalsIgnoreCase("addinheritance")) {
							p.sendMessage(Color.chat("&cUsage: /rank addinheritance <rank> <rank>"));
						} else if (args[0].equalsIgnoreCase("removeinheritance")) {
							p.sendMessage(Color.chat("&cUsage: /rank removeinheritance <rank> <rank>"));
						} else if (args[0].equalsIgnoreCase("list")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								String listedRanks = "";
								
								ArrayList<String> ranks = new ArrayList<String>();
								
								ranks.addAll(Ranks.get().getStringList("ranks.ranklist"));
								
								for (int i = 0; i < ranks.size(); i++) {
									listedRanks = listedRanks + "  &7&m-&r &" + Ranks.get().getString("ranks." + ranks.get(i) + ".guicolor") + Ranks.get().getString("ranks." + ranks.get(i) + ".name") + "\n";
								}
								
								p.sendMessage(Color.chat("&eRanks:"));
								p.sendMessage(Color.chat(listedRanks));
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								try {
									
									PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT `NAME` FROM ranks");
									
									ResultSet results = ps.executeQuery();
									
									String listedRanks = "";
									
									ArrayList<String> ranks = new ArrayList<String>();
									
									while (results.next()) {
										ranks.add(results.getString("NAME"));
									}
									
									for (int i = 0; i < ranks.size(); i++) {
										listedRanks = listedRanks + "  &7&m-&r &" + plugin.data.getGUIColor(ranks.get(i)) + ranks.get(i) + "\n";
									}
									
									p.sendMessage(Color.chat("&eRanks:"));
									p.sendMessage(Color.chat(listedRanks));
									
								} catch (SQLException e) {
									
									e.printStackTrace();
									
								}
								
							}

						} else if (args[0].equalsIgnoreCase("setdefault")) {
							p.sendMessage(Color.chat("&cUsage: /rank setdefault <rank>"));
						} else if (args[0].equalsIgnoreCase("create")) {
							p.sendMessage(Color.chat("&cUsage: /rank create <rank>"));
						} else if (args[0].equalsIgnoreCase("setguicolor")) {
							p.sendMessage(Color.chat("&cUsage: /rank setguicolor <rank> <color>"));
						} else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("information")) {
							p.sendMessage(Color.chat("&cUsage: /rank info <player/rank>"));
						} else {
							p.sendMessage(Color.chat("&cUsage: /rank help"));
						}
					} else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("setdefault")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								if (Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
									if (!Ranks.get().getBoolean("ranks." + args[1].toLowerCase() + ".default")) {
										ArrayList<String> sub = new ArrayList<String>();
										sub.addAll(Ranks.get().getStringList("ranks.ranklist"));
										for (String strValue : sub) {
											Ranks.get().set("ranks." + strValue.toLowerCase() + ".default", false);
											Ranks.save();
										}
										
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".default", true);
										Ranks.save();
										p.sendMessage(Color.chat("&eYou have set the &bdefault &erank to &a" + args[1].toLowerCase() + "&e."));
									}
								}
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
									
									
									if (ranks.contains(args[1].toLowerCase())) {
										
										if (defaultRank != null) {
										
											if (!plugin.data.isDefault(args[1].toLowerCase())) {
												plugin.data.setDefault(defaultRank, false);
												plugin.data.setDefault(args[1].toLowerCase(), true);
												
												p.sendMessage(Color.chat("&eYou have set the &bdefault &erank to &a" + args[1].toLowerCase() + "&e."));
											} else {
												p.sendMessage(Color.chat("&cThe specified rank is already the default."));
											}
										} else {
											p.sendMessage(Color.chat("&cAn error occured and the default rank is null. Please reinstall the plugin."));
										}
									} else {
										p.sendMessage(Color.chat("&cThe specified rank does not exist."));
									}
									
									
									
									
									
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
							}
						
						} else if (args[0].equalsIgnoreCase("addperm") || args[0].equalsIgnoreCase("addpermission")) {
							p.sendMessage(Color.chat("&cUsage: /rank addperm <player/rank> <permission>"));
						} else if (args[0].equalsIgnoreCase("removeperm") || args[0].equalsIgnoreCase("removepermission")) {
							p.sendMessage(Color.chat("&cUsage: /rank removeperm <player/rank> <permission>"));	
						} else if (args[0].equalsIgnoreCase("setprefix")) {
							p.sendMessage(Color.chat("&cUsage: /rank setprefix <rank> <prefix>"));
						} else if (args[0].equalsIgnoreCase("set")) {
							p.sendMessage(Color.chat("&cUsage: /rank set <player> <rank>"));
						} else if (args[0].equalsIgnoreCase("addinheritance")) {
							p.sendMessage(Color.chat("&cUsage: /rank addinheritance <rank> <rank>"));
						} else if (args[0].equalsIgnoreCase("removeinheritance")) {
							//p.sendMessage(Color.chat("&cUsage: /rank removeinheritance <rank> <rank>"));
						} else if (args[0].equalsIgnoreCase("create")) {
							if (args[1].length() >= 16) {
								p.sendMessage(Color.chat("&cRank must be less than 16 characters."));
							} else if (args[1].equalsIgnoreCase("ranklist")){ 
								p.sendMessage(Color.chat("&cA rank cannot be named this."));
							} else {
								if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
									if (!Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
										ArrayList<String> inherit = new ArrayList<String>();
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".prefix", "&a");
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".name", args[1].toLowerCase());
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".default", false);
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".inheritence", inherit);
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".guicolor", 'a');
										ArrayList<String> perms = new ArrayList<String>();
										ArrayList<String> list = new ArrayList<String>();
										list.addAll(Ranks.get().getStringList("ranks.ranklist"));
										list.add(args[1].toLowerCase());
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".permissions", perms);
										Ranks.get().set("ranks.ranklist", list);
										Ranks.save();
										
										
										
										p.sendMessage(Color.chat("&eYou have created a rank named: &a" + args[1]));
									} else {
										p.sendMessage(Color.chat("&cThat is already a rank."));
									}
								} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
									
									if (plugin.SQL.isConnected()) {
										if (!plugin.data.exists(args[1].toLowerCase())) {
										
										
										
											plugin.data.createRank(args[1].toLowerCase());
											
											
											
											
											
											p.sendMessage(Color.chat("&eYou have created a rank named: &a" + args[1]));
										} else {
											p.sendMessage(Color.chat("&cThat is already a rank."));
										}
									} else {
										p.sendMessage(Color.chat("&cThe database is not connected."));
									}
									
								}
							}
						} else if (args[0].equalsIgnoreCase("remove")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								if (Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
									if (!Ranks.get().getBoolean("ranks." + args[1].toLowerCase() + ".default")) {
										Ranks.get().set("ranks." + args[1].toLowerCase(), null);
										ArrayList<String> list = new ArrayList<String>();
										list.addAll(Ranks.get().getStringList("ranks.ranklist"));
										list.remove(args[1].toLowerCase());
										Ranks.get().set("ranks.ranklist", list);
										Ranks.save();
										p.sendMessage(Color.chat("&eYou have removed the rank &a" + args[1] + "&e."));
										 
									} else {
										p.sendMessage(Color.chat("&cYou cannot delete this rank."));
									}
								} else {
									p.sendMessage(Color.chat("&cThe specified rank does not exist."));
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								if (plugin.data.exists(args[1].toLowerCase())) {
									
									if (!plugin.data.isDefault(args[1].toLowerCase())) {
										
										try {
												
											PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM ranks WHERE NAME=?");
											ps.setString(1, args[1].toLowerCase());
											
											ps.executeUpdate();
											
											PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("SELECT `NAME` FROM ranks");
											
											ResultSet rs = ps2.executeQuery();
											while (rs.next()) {
												
												if (plugin.data.getListInheritence(rs.getString("NAME")).contains(args[1].toLowerCase())) {
													plugin.data.removeInheritance(rs.getString("NAME"), args[1].toLowerCase());
												}
												
											}
											
											p.sendMessage(Color.chat("&eYou have removed the rank &a" + args[1] + "&e."));
											
										} catch (SQLException e) {
											e.printStackTrace();
										}
										
									} else {
										p.sendMessage(Color.chat("&cYou cannot delete this rank."));
									}
									
								} else {
									p.sendMessage(Color.chat("&cThe specified rank does not exist."));
								}
								
							}
						} else if (args[0].equalsIgnoreCase("setguicolor")) {
							p.sendMessage(Color.chat("&cUsage: /rank setguicolor <rank> <color>"));
						} else if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("information")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								Player target = Bukkit.getPlayer(args[1]);
								if (target != null) {
									p.sendMessage(Color.chat("              &b" + target.getName() + "&e's Info"));
									p.sendMessage(Color.chat("&7&m--------------------------------"));
									p.sendMessage(Color.chat("&eRank&7:&r &" + Ranks.get().getString("ranks." + Players.get().getString(target.getUniqueId().toString() + ".profile.rank") + ".guicolor") + Ranks.get().getString("ranks." + Players.get().getString(target.getUniqueId().toString() + ".profile.rank") + ".name")));
								
									String line = "";
									
									
									
									if (Players.get().getStringList(target.getUniqueId().toString() + ".profile.permissions").size() != 0) {
										
										for (int i = 0; i < Players.get().getStringList(target.getUniqueId().toString() + ".profile.permissions").size(); i++) {
											
											line = line + "&7  - &"  + Ranks.get().getString("ranks." + Players.get().getString(target.getUniqueId().toString() + ".profile.rank") + ".guicolor")+ Players.get().getStringList(target.getUniqueId().toString() + ".profile.permissions").get(i) + "\n";
											
										}
										
										p.sendMessage(Color.chat("&ePermissions: "));
										p.sendMessage(Color.chat(line));
									
									
									}
									
									p.sendMessage(Color.chat("&eUUID&7:&r &" + Ranks.get().getString("ranks." + Players.get().getString(target.getUniqueId().toString() + ".profile.rank") + ".guicolor") + target.getUniqueId()));
									p.sendMessage(Color.chat("&7&m--------------------------------"));
								
								
								
								} else if (Ranks.get().getStringList("ranks.ranklist").contains(args[1].toLowerCase())) {
									
									String line = "";
									
									p.sendMessage(Color.chat("              &" + Ranks.get().getString("ranks." + args[1].toLowerCase() + ".guicolor") + Ranks.get().getString("ranks." + args[1].toLowerCase() + ".name") + " &eRank's Info"));
									p.sendMessage(Color.chat("&7&m--------------------------------"));
									
									if (Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".permissions").size() != 0) {	
										for (int i = 0; i < Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".permissions").size(); i++) {
											line = line + "&7  - &" + Ranks.get().getString("ranks." + args[1].toLowerCase() + ".guicolor") + Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".permissions").get(i) + "\n";
										}
										
										p.sendMessage(Color.chat("&ePermissions: "));
										p.sendMessage(Color.chat(line));
									}
									
									
									int playersWith = 0;
									
									
									for (String str : Players.get().getConfigurationSection("").getKeys(false)) {
										if (Players.get().getString(str + ".profile.rank").equals(args[1].toLowerCase())) { 
											playersWith++; 
									 	} 
									}
									 
									
									p.sendMessage(Color.chat("&ePlayers With Rank&7:&r &" + Ranks.get().getString("ranks." + args[1].toLowerCase() + ".guicolor") + playersWith));
									p.sendMessage(Color.chat("&7&m--------------------------------"));
									
								} else {
									p.sendMessage(Color.chat("&cInvalid player/rank."));
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								Player target = Bukkit.getPlayer(args[1]);
								if (target != null) {
									if (plugin.data.exists(target.getUniqueId())) {
										p.sendMessage(Color.chat("              &" + plugin.data.getGUIColor(plugin.data.getRank(target.getUniqueId())) + target.getName() + "&e's Info"));
										p.sendMessage(Color.chat("&7&m--------------------------------"));
										p.sendMessage(Color.chat("&eRank&7:&r &" + plugin.data.getGUIColor(plugin.data.getRank(target.getUniqueId())) + plugin.data.getRank(target.getUniqueId())));
									
										String line = "";
										
										List<String> perms = new ArrayList<String>();
										perms.addAll(plugin.data.getListPermissions(target.getUniqueId()));
										
										
										if (perms.size() != 0) {
											
											for (int i = 0; i < perms.size(); i++) {
												line = line + "&7  - &" +  plugin.data.getGUIColor(plugin.data.getRank(target.getUniqueId())) + perms.get(i) + "\n";
											}
											
											
											p.sendMessage(Color.chat("&ePermissions: "));
											p.sendMessage(Color.chat(line));
											
										}
										
										p.sendMessage(Color.chat("&eUUID&7:&r &" + plugin.data.getGUIColor(plugin.data.getRank(target.getUniqueId())) + target.getUniqueId()));
										p.sendMessage(Color.chat("&7&m--------------------------------"));
									}
								} else if (plugin.data.exists(args[1].toLowerCase())) {
									
									String line = "";
									
									p.sendMessage(Color.chat("              &" + plugin.data.getGUIColor(args[1].toLowerCase()) + args[1].toLowerCase()  + " &eRank's Info"));
									p.sendMessage(Color.chat("&7&m--------------------------------"));
									
									List<String> perms = plugin.data.getListPermissions(args[1].toLowerCase());
									
									if (perms != null && perms.size() != 0) {	
										for (int i = 0; i < perms.size(); i++) {
											line = line + "&7  - &" + plugin.data.getGUIColor(args[1].toLowerCase()) + perms.get(i) + "\n";
										}
										
										p.sendMessage(Color.chat("&ePermissions: "));
										p.sendMessage(Color.chat(line));
									}
									
									
									int playersWith = 0;
									
									try {
										
										PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT `UUID` FROM playerdata");
										
										ResultSet rs = ps.executeQuery();
										
										while (rs.next()) {
											if (plugin.data.getRank(UUID.fromString(rs.getString("UUID"))).equals(args[1].toLowerCase())) {
												playersWith = playersWith + 1;
											}
										}
										
									} catch (SQLException e) {
										e.printStackTrace();
									}
									
									p.sendMessage(Color.chat("&ePlayers With Rank&7:&r &" + plugin.data.getGUIColor(args[1].toLowerCase()) + playersWith));
									p.sendMessage(Color.chat("&7&m--------------------------------"));
									
									
								} else {
									p.sendMessage(Color.chat("&cInvalid player/rank."));
								}
								
							}
						}  else {
							p.sendMessage(Color.chat("&cUsage: /rank help"));
						}
					} else if (args.length == 3) {
						if (args[0].equalsIgnoreCase("addpermission") || args[0].equalsIgnoreCase("addperm")) {
								
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
								if (Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
									if (!args[2].equals("*")) {
										
										PermissionHandler ph = new PermissionHandler(plugin);
										
										ArrayList<String> permissions = new ArrayList<String>();
										permissions.addAll(Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".permissions"));
										permissions.add(args[2]);
										
										
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".permissions", permissions);
										Ranks.save();
										
										p.sendMessage(Color.chat("&eYou have added permission &b" + args[2] + " &eto rank &a" + args[1] + "&e."));
										
										for (String addperm : permissions) {
											for (Player e : Bukkit.getOnlinePlayers()) {
												if (Players.get().getString(e.getUniqueId().toString() + ".profile.rank").equals(args[1].toLowerCase())) {
													ph.addPermission(e, addperm);
												} else {
													return true;
													
												}	
												
												
											}
										}	
									} else {
										p.sendMessage(Color.chat("&cThis permission is not supported, as to use it would require breaking Bukkit's permission code."));
									}
								} else {
									if (!args[2].equals("*")) {
										Player pe = Bukkit.getPlayer(args[1]);
										if (pe != null) {
											
											PermissionHandler ph = new PermissionHandler(plugin);
											
											ArrayList<String> perms = new ArrayList<String>();
											perms.addAll(Players.get().getStringList(pe.getUniqueId().toString() + ".profile.permissions"));
											perms.add(args[2].toLowerCase());
											Players.get().set(pe.getUniqueId().toString() + ".profile.permissions", perms);
											Players.save();
	
											
											ph.addPermission(pe, args[2].toLowerCase());
												
											
											
											p.sendMessage(Color.chat("&eYou have added permission &b" + args[2] + " &eto player &a" + args[1] + "&e."));
										} else {
											p.sendMessage(Color.chat("&cUsage: /rank addperm <player/rank> <permission>"));
										}
									} else {
										p.sendMessage(Color.chat("&cThis permission is not supported, as to use it would require breaking Bukkit's permission code."));
									}
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								
								try {
									if (plugin.data.exists(args[1].toLowerCase())) {
										if (!args[2].equals("*")) {
											
											PermissionHandler ph = new PermissionHandler(plugin);
											
											PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT `UUID` FROM playerdata");
											
											@SuppressWarnings("unused")
											ResultSet rs = ps.executeQuery();
											
											plugin.data.addPermission(args[1].toLowerCase(), args[2]);;
											
											p.sendMessage(Color.chat("&eYou have added permission &b" + args[2] + " &eto rank &a" + args[1] + "&e."));
												
											for (Player e : Bukkit.getOnlinePlayers()) {
														
												if (plugin.data.getRank(e.getUniqueId()).equals(args[1].toLowerCase())) {
																
													ph.addPermission(e, args[2]);
														
												}
												
											}
											
											
										} else {
											p.sendMessage(Color.chat("&cThis permission is not supported, as to use it would require breaking Bukkit's permission code."));
										}
									} else {
										if (!args[2].equals("*")) {
											Player pe = Bukkit.getPlayer(args[1]);
											if (pe != null) {
												if (plugin.data.exists(pe.getUniqueId())) {				
													PermissionHandler ph = new PermissionHandler(plugin);						
													plugin.data.addPermission(pe.getUniqueId(), args[2]);;			
													ph.addPermission(pe, args[2]);	
													
													p.sendMessage(Color.chat("&eYou have added permission &b" + args[2] + " &eto player &a" + args[1] + "&e."));
												} else {
													p.sendMessage(Color.chat("&cUsage: /rank addperm <player/rank> <permission>"));
												}
											} else {
												p.sendMessage(Color.chat("&cUsage: /rank addperm <player/rank> <permission>"));
											}
										} else {
											p.sendMessage(Color.chat("&cThis permission is not supported, as to use it would require breaking Bukkit's permission code."));
										}	
									}
								} catch (SQLException e) {
									e.printStackTrace();
								}
								
								
							}
						} else if (args[0].equalsIgnoreCase("set")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								Player arg = Bukkit.getPlayer(args[1]);
								if (arg != null) {
									if (Ranks.get().isConfigurationSection("ranks." + args[2].toLowerCase())) {          
										Players.get().set(arg.getUniqueId().toString() + ".profile.rank", args[2].toLowerCase());
										Players.save();
										p.sendMessage(Color.chat("&eYou have set &b" + args[1] + "&e's rank to: &a" + args[2]));
									} else {
										p.sendMessage(Color.chat("&cThat is not a valid rank."));
									}
								} else {
									p.sendMessage(Color.chat("&cThat is not a valid player."));
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								Player arg = Bukkit.getPlayer(args[1]);
								if (arg != null) {
									if (plugin.data.exists(args[2].toLowerCase())) {
										
										
										plugin.data.setRank(arg.getUniqueId(), args[2].toLowerCase());
										p.sendMessage(Color.chat("&eYou have set &b" + args[1] + "&e's rank to: &a" + args[2]));
										
										
										
									} else {
										
										p.sendMessage(Color.chat("&cThat is not a valid rank."));
										
									}
									
								} else {
									p.sendMessage(Color.chat("&cThat is not a valid player."));
								}
								
							}
						} else if (args[0].equalsIgnoreCase("setprefix")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								if (Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
									Ranks.get().set("ranks." + args[1].toLowerCase() + ".prefix", args[2]);
									Ranks.save();
									p.sendMessage(Color.chat("&eYou have set &b" + args[1] + "&e's prefix to: &a" + args[2]));
								} else {
									p.sendMessage(Color.chat("&cUsage: /rank setprefix <rank> <prefix>"));
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								if (plugin.data.exists(args[1].toLowerCase())) {
									
									plugin.data.setPrefix(args[1].toLowerCase(), args[2]);
									p.sendMessage(Color.chat("&eYou have set &b" + args[1] + "&e's prefix to: &a" + args[2]));
									
								} else {
									p.sendMessage(Color.chat("&cUsage: /rank setprefix <rank> <prefix>"));
								}
								
							}
						} else if (args[0].equalsIgnoreCase("removepermission") || args[0].equalsIgnoreCase("removeperm")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
								if (Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
									ArrayList<String> rankperms = new ArrayList<String>();
									rankperms.addAll(Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".permissions"));
									if (rankperms.contains(args[2])) {
										
										PermissionHandler ph = new PermissionHandler(plugin);
										rankperms.remove(args[2]);
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".permissions", rankperms);
										Ranks.save();
										
										for (Player u : Bukkit.getOnlinePlayers()) {
											if (Players.get().getString(u.getUniqueId().toString() + ".profile.rank").equalsIgnoreCase(args[1])) {
												ph.removePermission(u, args[2]);
												//u.kickPlayer(Color.chat("&cA permission has been taken away from your rank. Please log back in."));
											}
										}
										
										p.sendMessage(Color.chat("&eYou have removed the permission &b" + args[2] + "&e from the rank &a" + args[1]));
									} else {
										p.sendMessage(Color.chat("&cThe specified rank does not have the specified permission."));
									}
								} else {
									Player pe = Bukkit.getPlayer(args[1]);
									if (pe != null) {
										ArrayList<String> rankperms = new ArrayList<String>();
										rankperms.addAll(Players.get().getStringList(pe.getUniqueId().toString() + ".profile.permissions"));
										if (rankperms.contains(args[2])) {
											PermissionHandler ph = new PermissionHandler(plugin);
											
											rankperms.remove(args[2]);
											Players.get().set(pe.getUniqueId().toString() + ".profile.permissions", rankperms);
											Players.save();
											
											ph.removePermission(pe, args[2]);
											//pe.kickPlayer(Color.chat("&cA permission was removed from you. Please log back in."));
											p.sendMessage(Color.chat("&eYou have removed the permission &b" + args[2] + "&e from the player &a" + pe.getDisplayName()));
										} else {
											p.sendMessage(Color.chat("&cThe specified player does not have the specified permission."));
										}
									} else {
										p.sendMessage(Color.chat("&cUsage: /rank removeperm <player/rank> <permission>"));	
									}
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								if (plugin.data.exists(args[1].toLowerCase())) {
									
									List<String> perms = plugin.data.getListPermissions(args[1].toLowerCase());
									if (perms.contains(args[2])) {
										
										PermissionHandler ph = new PermissionHandler(plugin);
										plugin.data.removePermission(args[1].toLowerCase(), args[2]);
										
										for (Player u : Bukkit.getOnlinePlayers()) {
											if (plugin.data.getRank(u.getUniqueId()).equals(args[1].toLowerCase())) {
												ph.removePermission(u, args[2]);
												//u.kickPlayer(Color.chat("&cA permission has been taken away from your rank. Please log back in."));
											}
										}
										
										p.sendMessage(Color.chat("&eYou have removed the permission &b" + args[2] + "&e from the rank &a" + args[1]));
										
									} else {
										p.sendMessage(Color.chat("&cThe specified rank does not have the specified permission."));
									}
									
								} else {
									Player pe = Bukkit.getPlayer(args[1]);
									if (pe != null) {
										
										if (plugin.data.exists(pe.getUniqueId())) {
											
											List<String> perms = plugin.data.getListPermissions(pe.getUniqueId());
											if (perms.contains(args[2])) {
												
												PermissionHandler ph = new PermissionHandler(plugin);
												plugin.data.removePermission(pe.getUniqueId(), args[2]);
												ph.removePermission(pe, args[2]);
												
												p.sendMessage(Color.chat("&eYou have removed the permission &b" + args[2] + "&e from the player &a" + pe.getDisplayName()));
											} else {
												p.sendMessage(Color.chat("&cThe specified player does not have the specified permission."));
											}
											
										} else {
											p.sendMessage(Color.chat("&cUsage: /rank removeperm <player/rank> <permission>"));	
										}
										
									} else {
										p.sendMessage(Color.chat("&cUsage: /rank removeperm <player/rank> <permission>"));	
									}
								}
								
							}
						} else if (args[0].equalsIgnoreCase("setcolor")) {
							if (Ranks.get().isConfigurationSection("ranks." + args[1].toLowerCase())) {
								if (args[2].equals("1") || args[2].equals("2")|| args[2].equals("3")|| args[2].equals("4")|| args[2].equals("5")|| args[2].equals("6")|| args[2].equals("7")|| args[2].equals("8")|| args[2].equals("9")|| args[2].equals("0")|| args[2].equalsIgnoreCase("a")|| args[2].equalsIgnoreCase("b")|| args[2].equalsIgnoreCase("c")|| args[2].equalsIgnoreCase("d")|| args[2].equalsIgnoreCase("e") || args[2].equalsIgnoreCase("f")) {
									Ranks.get().set("ranks." + args[1].toLowerCase() + ".guicolor", args[2].toLowerCase());
									Ranks.save();
									p.sendMessage(Color.chat("&eYou have set &a" + args[1]  + "&e's GUI color to &" + args[2] + "this color&e."));
								} else {
									p.sendMessage(Color.chat("&cPlease enter a valid color code. It cannot contain &."));
								}
							} else {
								p.sendMessage(Color.chat("&cThat is not a valid rank."));
							}
						} else if (args[0].equalsIgnoreCase("addinheritance")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								if (Ranks.get().isConfigurationSection("ranks."+ args[1].toLowerCase())) {
									if (Ranks.get().isConfigurationSection("ranks."+ args[2].toLowerCase())) {
										
										ArrayList<String> rankInheritence = new ArrayList<String>();
										
										rankInheritence.addAll(Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".inheritence"));
										rankInheritence.add(args[2].toLowerCase());
										
										PermissionHandler ph = new PermissionHandler(plugin);
										
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".inheritence", rankInheritence);
										Ranks.save();
										p.sendMessage(Color.chat("&eYou have added to &b" + args[1].toLowerCase() + "&e's inheritence by adding &a" + args[2].toLowerCase() + "&e."));
										
										ArrayList<String> addedPerms = new ArrayList<String>();
										addedPerms.addAll(Ranks.get().getStringList("ranks." + args[2].toLowerCase() + ".permissions"));
										
										for (Player d : Bukkit.getOnlinePlayers() ) {
											if (Players.get().getString(d.getUniqueId().toString() + ".profile.rank").equals(args[1].toLowerCase())) {
												for (int i = 0; i < addedPerms.size(); i++) {
													ph.addPermission(d, addedPerms.get(i));
												}
											}
										}
										
										
										
									} else {
										p.sendMessage(Color.chat("&cThat is not a valid rank to set inheritence to."));
									}
								} else {
									p.sendMessage(Color.chat("&cThat is not a valid rank.")); 
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								if (plugin.data.exists(args[1].toLowerCase())) {
									if (plugin.data.exists(args[2].toLowerCase())) {
										
										plugin.data.addInheritance(args[1].toLowerCase(), args[2].toLowerCase());
										p.sendMessage(Color.chat("&eYou have added to &b" + args[1].toLowerCase() + "&e's inheritence by adding &a" + args[2].toLowerCase() + "&e."));
										
										PermissionHandler ph = new PermissionHandler(plugin);
										
										List<String> permissions = plugin.data.getListPermissions(args[2].toLowerCase());
										
										if (permissions != null) {
										
											for (Player d : Bukkit.getOnlinePlayers() ) {
												if (plugin.data.getRank(d.getUniqueId()).equals(args[1].toLowerCase())) {
													for (int i = 0; i < permissions.size(); i++) {
														ph.addPermission(d, permissions.get(i));
													}
												}
											}
										}
										
										
									} else {
										p.sendMessage(Color.chat("&cThat is not a valid rank to set inheritence to."));
									}
									
								} else {
									p.sendMessage(Color.chat("&cThat is not a valid rank.")); 
								}
								
								
							}
							
						} else if (args[0].equalsIgnoreCase("setguicolor")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
								
							
								if (Ranks.get().getStringList("ranks.ranklist").contains(args[1].toLowerCase())) {
									if (!args[2].contains("&")) {
										if (args[2].equalsIgnoreCase("a") || args[2].equalsIgnoreCase("b") || args[2].equalsIgnoreCase("c") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("e") || args[2].equalsIgnoreCase("0") || args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("f") || args[2].equalsIgnoreCase("2") || args[2].equalsIgnoreCase("3") || args[2].equalsIgnoreCase("4") || args[2].equalsIgnoreCase("5") || args[2].equalsIgnoreCase("6") || args[2].equalsIgnoreCase("7") || args[2].equalsIgnoreCase("8") || args[2].equalsIgnoreCase("9")) {
											Ranks.get().set("ranks." + args[1].toLowerCase() + ".guicolor", args[2].toLowerCase());
											Ranks.save();
											
											p.sendMessage(Color.chat("&eYou have set &b" + args[1].toLowerCase() + "&e's GUI color to &" + args[2].toLowerCase() + "this&e." ));
										} else {
											p.sendMessage(Color.chat("&cPlease use a valid color code."));
										}
									} else {
										p.sendMessage(Color.chat("&cYour color does not need '&'."));
									}
								} else {
									p.sendMessage(Color.chat("&cThat is not a valid rank."));
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								if (plugin.data.exists(args[1].toLowerCase())) {
									
									if (!args[2].contains("&")) {
										if (args[2].equalsIgnoreCase("a") || args[2].equalsIgnoreCase("b") || args[2].equalsIgnoreCase("c") || args[2].equalsIgnoreCase("d") || args[2].equalsIgnoreCase("e") || args[2].equalsIgnoreCase("0") || args[2].equalsIgnoreCase("1") || args[2].equalsIgnoreCase("f") || args[2].equalsIgnoreCase("2") || args[2].equalsIgnoreCase("3") || args[2].equalsIgnoreCase("4") || args[2].equalsIgnoreCase("5") || args[2].equalsIgnoreCase("6") || args[2].equalsIgnoreCase("7") || args[2].equalsIgnoreCase("8") || args[2].equalsIgnoreCase("9")) {
											
											plugin.data.setGUIColor(args[1].toLowerCase(), args[2].toLowerCase());
											p.sendMessage(Color.chat("&eYou have set &b" + args[1].toLowerCase() + "&e's GUI color to &" + args[2].toLowerCase() + "this&e." ));
											
										} else {
											p.sendMessage(Color.chat("&cPlease use a valid color code."));
										}
									} else {
										p.sendMessage(Color.chat("&cYour color does not need '&'."));
									}
								} else {
									p.sendMessage(Color.chat("&cThat is not a valid rank."));
								}
								
							}
							
						} else if (args[0].equalsIgnoreCase("removeinheritance")) {
							
							if (plugin.getConfig().getString("settings.ConfigurationType").equals("FLAT_FILE")) {
							
								if (Ranks.get().isConfigurationSection("ranks."+ args[1].toLowerCase())) {
									if (Ranks.get().isConfigurationSection("ranks."+ args[2].toLowerCase())) {
										
										ArrayList<String> rankInheritence = new ArrayList<String>();
										
										rankInheritence.addAll(Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".inheritence"));
										rankInheritence.remove(args[2].toLowerCase());
										
										Ranks.get().set("ranks." + args[1].toLowerCase() + ".inheritence", rankInheritence);
										Ranks.save();
										
										PermissionHandler ph = new PermissionHandler(plugin);
										
										p.sendMessage(Color.chat("&eYou have removed &a" + args[2].toLowerCase() + " &efrom &b" + args[1].toLowerCase() + "&e's inheritence&e."));
										
										
										for (Player d : Bukkit.getOnlinePlayers()) {
											if (Players.get().getString(d.getUniqueId().toString() + ".profile.rank").equals(args[1].toLowerCase())) {
												
												ArrayList<String> originPerms = new ArrayList<String>();
												ArrayList<String> inheritencePerms = new ArrayList<String>();
												
												
												originPerms.addAll(Ranks.get().getStringList("ranks." + args[1].toLowerCase() + ".permissions"));
												inheritencePerms.addAll(Ranks.get().getStringList("ranks." + args[2].toLowerCase() + ".permissions"));
												
												for (int i = 0; i < originPerms.size(); i++) {
													ph.removePermission(d, originPerms.get(i));
												}
												
												for (int i = 0; i < inheritencePerms.size(); i++) {
													ph.removePermission(d, inheritencePerms.get(i));
												}
												
												
												
												for (int i = 0; i < originPerms.size(); i++) {
													ph.addPermission(d, originPerms.get(i));
												}
												
												
											}
										}
										
									}
								}
							} else if (plugin.getConfig().getString("settings.ConfigurationType").equals("SQL")) {
								
								if (plugin.data.exists(args[1].toLowerCase())) {
									if (plugin.data.exists(args[2].toLowerCase())) {
										
										List<String> inherit = new ArrayList<String>();
										
										inherit.addAll(plugin.data.getListInheritence(args[1].toLowerCase()));
										
										if (inherit.contains(args[2].toLowerCase())) {
											PermissionHandler ph = new PermissionHandler(plugin);
											
											p.sendMessage(Color.chat("&eYou have removed &a" + args[2].toLowerCase() + " &efrom &b" + args[1].toLowerCase() + "&e's inheritence&e."));
											
											plugin.data.removeInheritance(args[1].toLowerCase(), args[2].toLowerCase());
											
											List<String> originPerms = plugin.data.getListPermissions(args[1].toLowerCase());
											List<String> inheritencePerms = plugin.data.getListInheritence(args[2].toLowerCase());
											
											
											for (Player d : Bukkit.getOnlinePlayers()) {
												
												if (plugin.data.getRank(d.getUniqueId()).equals(args[1].toLowerCase())) {
													
													if (originPerms != null && inheritencePerms != null) {
														for (int i = 0; i < originPerms.size(); i++) {
															ph.removePermission(d, originPerms.get(i));
														}
														
														for (int i = 0; i < inheritencePerms.size(); i++) {
															ph.removePermission(d, inheritencePerms.get(i));
														}
														
														
														
														for (int i = 0; i < originPerms.size(); i++) {
															ph.addPermission(d, originPerms.get(i));
														}
													}
												}
											}
										} else {
											p.sendMessage(Color.chat("&cUsage: /rank removeinheritance <rank> <rank>"));
										}
									}
								}
								
							}
						}  else {
							p.sendMessage(Color.chat("&cUsage: /rank help"));
						}
					} else {
						p.sendMessage(Color.chat("&cUsage: /rank help"));
					}
				
			} else {
				sender.sendMessage(Color.chat("&cThis command is only executable ingame."));
			}
		}
		return false;
	}
	
	
	

}
