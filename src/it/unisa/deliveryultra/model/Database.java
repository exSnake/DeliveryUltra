/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author exSna
 *
 */
public abstract class Database {
	protected String dbName;
	protected String username;
	protected String password;
	protected String server;
	protected int port;
	protected Connection conn;
	
	protected Database(String server, int port, String dbName, String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.server = server;
		this.port = port;
		this.dbName = dbName;
	}
	
	public String getDbName() { return dbName; }
	public void setDbName(String dbName) {this.dbName = dbName; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username;}
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getServer() { return server; }
	public void setServer(String server) { this.server = server; }
	
	public int getPort() { return port; }
	public void setPort(int port) { this.port = port; }
	
	public Connection getConn() { return conn; }
	public void setConn(Connection conn) { this.conn = conn; }
	
	public abstract String getConnectionString();
	public abstract boolean openConnection();
	public abstract boolean closeConnection();
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString());
		str.append(String.format("%nServer: %s%n"
				+ "Port: %d%n"
				+ "dbName: %s%n"
				+ "Username: %s%n"
				+ "Password: %s%n"
				+ "ConnectionString: %s", getServer(),getPort(),getDbName(),getUsername(),getPassword(),getConnectionString()));
		return str.toString();
	}
	
}
