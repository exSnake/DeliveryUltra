/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	
	public abstract String getConnectionString();
	public abstract Connection openConnection();
	public abstract boolean closeConnection(Connection conn);
	
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

	public abstract ArrayList<Ristorante> getRistoranti() throws Exception;

	public abstract ArrayList<Cliente> getClienti() throws Exception;
	
	public abstract ArrayList<Indirizzo> getClienteIndirizzi(Cliente cliente) throws Exception;
	
	public abstract boolean inserisciOrdine(Ordine ordine, Ristorante ristorante, Cliente cliente) throws Exception;

	public abstract boolean assegnaOrdine(Ordine ordine, Persona persona, LocalDateTime ora) throws Exception;

	public abstract List<Ordine> getOrdini() throws Exception;

	public abstract List<Ordine> getOrdiniByRistorante(Ristorante ristorante) throws Exception;

	public abstract boolean eliminaOrdine(Ordine ordine) throws Exception;

	public abstract boolean accettaOrdine(Ordine ordine) throws Exception;

	public abstract boolean consegnaOrdine(Ordine ordine, String nominativo) throws Exception;

	public abstract List<Ordine> getOrdiniInCoda() throws Exception;

	public abstract List<Ordine> getOrdiniInCodaByRistorante(Ristorante ristorante) throws Exception;

	public abstract List<Persona> getPersoneByRistoranteId(int ristoranteId) throws Exception;
	
}
