package me.sub.cRanks.Utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import me.sub.cRanks.Main.Main;

public class PermissionHandler {
	
	HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	
	Main plugin;
	
	public PermissionHandler(Main plugin) {
		this.plugin = plugin;
	}
	
	
	public void addPermission(Player player, String permission) {
		
		PermissionAttachment attachment = player.addAttachment(plugin);
		perms.put(player.getUniqueId(), attachment);
		
		PermissionAttachment pperms = perms.get(player.getUniqueId());
		pperms.setPermission(permission, true);
		
		
	}
	
	public void removePermission(Player player, String permission) {
		
		
		PermissionAttachment attachment = player.addAttachment(plugin);
		perms.put(player.getUniqueId(), attachment);
		
		PermissionAttachment pperms = perms.get(player.getUniqueId());
		perms.get(player.getUniqueId()).unsetPermission(permission);
		pperms.setPermission(permission, false);
		
	}

}
