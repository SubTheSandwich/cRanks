package me.sub.cRanks.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import me.sub.cRanks.Main.Main;

public class SQLGetter {

	private Main plugin;
	
	public SQLGetter(Main plugin) {
		this.plugin = plugin;
	}
	
	public void createTable() {
		PreparedStatement ps;
		
		try {
			ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS ranks " 
					+ "(NAME VARCHAR(100), PERMISSIONS VARCHAR(1000), INHERITANCE VARCHAR(100), ISDEFAULT BOOLEAN, GUICOLOR VARCHAR(1), PREFIX VARCHAR(100), PRIMARY KEY (NAME))");
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PreparedStatement ps2;
		
		try {
			ps2 = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playerdata " 
					+ "(UUID VARCHAR(100), PERMISSIONS VARCHAR(1000), RANK VARCHAR(100), PRIMARY KEY (UUID))");
			
			ps2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createRank(String rankName) {
		
		try {
			
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM ranks WHERE NAME=?");
			
			ps.setString(1, rankName);
			
			ResultSet results = ps.executeQuery();
			results.next();
			
			if (!exists(rankName)) {
				PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO ranks (NAME,GUICOLOR,PREFIX) VALUES (?,?,?)");
				
				ps2.setString(1, rankName);
				ps2.setString(2, "a");
				ps2.setString(3, "&a");
				ps2.executeUpdate();
				
				return;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void createPlayer(UUID playerUUID) {
		
		try {
			
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
			
			ps.setString(1, playerUUID.toString());
			
			ResultSet results = ps.executeQuery();
			results.next();
			
			if (!exists(playerUUID)) {
				PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playerdata (UUID,PERMISSIONS) VALUES (?,?)");
				
				ps2.setString(1, playerUUID.toString());
				ps2.setString(2, null);
				ps2.executeUpdate();
				
				System.out.println("test");
				
				return;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean exists(String rankName) {
		
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM ranks WHERE NAME=?");
			ps.setString(1, rankName);
			
			ResultSet results = ps.executeQuery();
			if (results.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean exists(UUID uuid) {
		
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playerdata WHERE UUID=?");
			ps.setString(1, uuid.toString());
			
			ResultSet results = ps.executeQuery();
			if (results.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void addPermission(String rankName, String permissionValue) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET PERMISSIONS=? WHERE NAME=?");
			
			String pulledString = getPermissions(rankName);
			
			if (pulledString != null) {
			
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				myList.add(permissionValue);
				
				String stringify = myList.toString();
				
				ps.setString(2, rankName);
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
			} else {
				
				List<String> newList = new ArrayList<String>();
				newList.add(permissionValue);
				
				
				String stringify = newList.toString();
				
				ps.setString(2, rankName);
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void addPermission(UUID uuid, String permissionValue) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET PERMISSIONS=? WHERE UUID=?");
			
			String pulledString = getPermissions(uuid);
			
			if (pulledString != null) {
			
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				myList.add(permissionValue);
				
				String stringify = myList.toString();
				
				ps.setString(2, uuid.toString());
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
			} else {
				
				List<String> newList = new ArrayList<String>();
				newList.add(permissionValue);
				
				
				String stringify = newList.toString();
				
				ps.setString(2, uuid.toString());
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removePermission(String rankName, String permissionValue) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET PERMISSIONS=? WHERE NAME=?");
			
			String pulledString = getPermissions(rankName);
			
			if (pulledString != null) {
			
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				myList.remove(permissionValue);
				
				if (myList.size() == 0) {
					ps.setString(2, rankName);
					ps.setString(1, null);
				} else {
					String stringify = myList.toString();
				
					ps.setString(2, rankName);
					ps.setString(1, stringify);
				}
				
				
				
				
				ps.executeUpdate();
			} else {
				
				List<String> newList = new ArrayList<String>();
				newList.remove(permissionValue);
				
				
				String stringify = newList.toString();
				
				ps.setString(2, rankName);
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void removePermission(UUID uuid, String permissionValue) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET PERMISSIONS=? WHERE UUID=?");
			
			String pulledString = getPermissions(uuid);
			
			if (pulledString != null) {
			
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				myList.remove(permissionValue);
				
				if (myList.size() == 0) {
					ps.setString(2, uuid.toString());
					ps.setString(1, null);
				} else {
					String stringify = myList.toString();
				
					ps.setString(2, uuid.toString());
					ps.setString(1, stringify);
				}
				
				
				
				ps.executeUpdate();
			} else {
				
				List<String> newList = new ArrayList<String>();
				newList.remove(permissionValue);
				
				
				String stringify = newList.toString();
				
				ps.setString(2, uuid.toString());
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void addInheritance(String rankName, String inheritance) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET INHERITANCE=? WHERE NAME=?");
			
			
			
			String pulledString = getInheritance(rankName);
			
			
			if (pulledString != null) {
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				myList.add(inheritance);
				
				String stringify = myList.toString();
				
				ps.setString(1, stringify);
				ps.setString(2, rankName);
				
				ps.executeUpdate();
			} else {
				List<String> newList = new ArrayList<String>();
				newList.add(inheritance);
				
				
				String stringify = newList.toString();
				
				ps.setString(2, rankName);
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<String> getListPermissions(String rankName) {
		String pulledString = getPermissions(rankName);
		
		
		if (pulledString != null) {
			String ad1 = pulledString.replace("[", "");
			String convert = ad1.replace("]", "");
			
			List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
			
			return myList;
			
		}
		return null;
	}
	
	public List<String> getListPermissions(UUID uuid) {
		String pulledString = getPermissions(uuid);
		
		
		if (pulledString != null) {
			String ad1 = pulledString.replace("[", "");
			String convert = ad1.replace("]", "");
			
			List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
			
			return myList;
			
		} else {
			List<String> myList = new ArrayList<String>();
			return myList;
		}

	}
	
	public List<String> getListInheritence(String rankName) {
		String pulledString = getInheritance(rankName);
		
		
		if (pulledString != null) {
			String ad1 = pulledString.replace("[", "");
			String convert = ad1.replace("]", "");
			
			List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
			
			return myList;
			
		} else {
			List<String> myList = new ArrayList<String>();
			return myList;
		}
		
	}
	
	public void removeInheritance(String rankName, String inheritance) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET INHERITANCE=? WHERE NAME=?");
			
			
			
			String pulledString = getInheritance(rankName);
			
			
			if (pulledString != null) {
				String ad1 = pulledString.replace("[", "");
				String convert = ad1.replace("]", "");
				
				List<String> myList = new ArrayList<String>(Arrays.asList(convert.split(", ")));
				
				
				myList.remove(inheritance);
				
				if (myList.size() != 0) {
				
					String stringify = myList.toString();
					
					ps.setString(1, stringify);
					ps.setString(2, rankName);
				} else {
					
					ps.setString(1, null);
					ps.setString(2, rankName);
				}
				
				ps.executeUpdate();
			} else {
				List<String> newList = new ArrayList<String>();
				newList.remove(inheritance);
				
				
				String stringify = newList.toString();
				
				ps.setString(2, rankName);
				ps.setString(1, stringify);
				
				
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getPermissions(String rankName) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PERMISSIONS FROM ranks WHERE NAME=?");
			ps.setString(1, rankName);
			
			ResultSet rs = ps.executeQuery();
			
			String listArrayString = "";
			
			if (rs.next()) {
				listArrayString = rs.getString("PERMISSIONS");
				return listArrayString;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPermissions(UUID uuid) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PERMISSIONS FROM playerdata WHERE UUID=?");
			ps.setString(1, uuid.toString());
			
			ResultSet rs = ps.executeQuery();
			
			String listArrayString = "";
			
			if (rs.next()) {
				listArrayString = rs.getString("PERMISSIONS");
				return listArrayString;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRank(UUID uuid) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT RANK FROM playerdata WHERE UUID=?");
			ps.setString(1, uuid.toString());
			
			ResultSet rs = ps.executeQuery();
			
			PreparedStatement ps1 = plugin.SQL.getConnection().prepareStatement("SELECT UUID FROM playerdata WHERE UUID=?");
			ps1.setString(1, uuid.toString());
			
			ResultSet rs1 = ps1.executeQuery();
			
			
			while (rs.next()) {
				rs1.next();
				if (rs1.getString("UUID").equals(uuid.toString())) {
					return rs.getString("RANK");
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getInheritance(String rankName) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT INHERITANCE FROM ranks WHERE NAME=?");
			ps.setString(1, rankName);
			
			ResultSet rs = ps.executeQuery();
			
			String listArrayString = "";
			
			if (rs.next()) {
				listArrayString = rs.getString("INHERITANCE");
				return listArrayString;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setDefault(String rankName, Boolean value) {
		
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET ISDEFAULT=? WHERE NAME=?");
			
			
			
			ps.setBoolean(1, value);
			ps.setString(2, rankName);
				
			ps.executeUpdate();
		
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void setGUIColor(String rankName, String value) {
		
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET GUICOLOR=? WHERE NAME=?");
			
			
			
			ps.setString(1, value);
			ps.setString(2, rankName);
				
			ps.executeUpdate();
		
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void setPrefix(String rankName, String value) {
		
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE ranks SET PREFIX=? WHERE NAME=?");
			
			
			
			ps.setString(1, value);
			ps.setString(2, rankName);
				
			ps.executeUpdate();
		
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public void setRank(UUID uuid, String rankName) {
		
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playerdata SET RANK=? WHERE UUID=?");
			
			
			ps.setString(1, rankName);
			ps.setString(2, uuid.toString());
				
			ps.executeUpdate();
		
		
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
	public String getDefault() {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT `NAME` FROM ranks");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (plugin.data.isDefault(rs.getString("NAME"))) {
					return rs.getString("NAME");
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getGUIColor(String rankName) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT GUICOLOR FROM ranks WHERE NAME=?");
			ps.setString(1, rankName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("GUICOLOR");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPrefix(String rankName) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PREFIX FROM ranks WHERE NAME=?");
			ps.setString(1, rankName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("PREFIX");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean isDefault(String rankName) {
		try {
			PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT ISDEFAULT FROM ranks WHERE NAME=?");
			ps.setString(1, rankName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBoolean("ISDEFAULT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
