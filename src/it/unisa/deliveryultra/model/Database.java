/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

	public abstract List<Ristorante> getRistoranti() throws SQLException;

	public abstract List<Cliente> getClienti() throws SQLException;
	
	public abstract List<Indirizzo> getClienteIndirizzi(Cliente cliente) throws SQLException;
	
	public abstract boolean inserisciOrdine(Ordine ordine, Ristorante ristorante, Cliente cliente) throws SQLException;

	public abstract boolean assegnaOrdine(Ordine ordine, Persona persona, LocalDateTime ora) throws SQLException, IOException;

	public abstract List<Ordine> getOrdini() throws SQLException;

	public abstract List<Ordine> getOrdiniByRistorante(Ristorante ristorante) throws SQLException;

	public abstract boolean eliminaOrdine(Ordine ordine) throws SQLException, IOException;

	public abstract boolean accettaOrdine(Ordine ordine) throws SQLException, IOException;

	public abstract boolean consegnaOrdine(Ordine ordine, String nominativo) throws SQLException, IOException;

	public abstract List<Ordine> getOrdiniInCoda() throws SQLException;

	public abstract List<Ordine> getOrdiniInCodaByRistorante(Ristorante ristorante) throws SQLException;

	public abstract List<Persona> getPersoneByRistoranteId(int ristoranteId) throws SQLException;

	public abstract List<Ordine> getOrdiniByCliente(String email) throws SQLException;

	public abstract List<Valutazione> getValutazioniEffettuateByCliente(String email) throws SQLException;

	public abstract List<Ordine> getOrdiniConsegnatiDaRiderNonValutati() throws SQLException;

	public abstract boolean checkRistoranteDisponibile(Ristorante ristorante) throws SQLException;

	public abstract List<Ristorante> getRistorantiDisponibili() throws SQLException;

	public abstract List<Persona> getPersoneByNominativoConsegna(String nominativo) throws SQLException;
	
}
