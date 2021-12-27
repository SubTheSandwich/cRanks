package me.sub.cRanks.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.sub.cRanks.Main.Main;

public class MySQL {

	
	private String host = Main.getInstance().getConfig().getString("settings.sql.host");
	private String port = Main.getInstance().getConfig().getString("settings.sql.port");
	private String database = Main.getInstance().getConfig().getString("settings.sql.database");
	private String username = Main.getInstance().getConfig().getString("settings.sql.username");
	private String password = Main.getInstance().getConfig().getString("settings.sql.password");
	
	private Connection connection;
	
	public boolean isConnected() {
		return (connection == null ? false : true);
	}
	
	public void connect() throws ClassNotFoundException, SQLException {
		if (!isConnected()) {
			connection = DriverManager.getConnection("jdbc:mysql://" +
				     host + ":" + port + "/" + database + "?useSSL=false",
				     username, password);
		}
		
	}
	
	public void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
