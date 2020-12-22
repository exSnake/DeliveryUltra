/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author exSna
 *
 */
public class DBMysql extends Database {
	private final String DBMS_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String ARGUMENTS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	/**
	 * @param server
	 * @param port
	 * @param dbName
	 * @param username
	 * @param password
	 */
	public DBMysql(String server, int port, String dbName, String username, String password) {
		super(server, port, dbName, username, password);
	}

	@Override
	public String getConnectionString() {
		return String.format("jdbc:mysql://%s:%d/%s%s", getServer(),getPort(),getDbName(),this.ARGUMENTS);
	}

	@Override
	public boolean openConnection(){
		try {
			Class.forName(DBMS_DRIVER);		
			DriverManager.getConnection(getConnectionString(), getUsername(), getPassword());
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
