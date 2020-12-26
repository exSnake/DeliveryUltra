/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

/**
 * @author exSna
 *
 */
public class DBMysql extends Database {
	private final String DBMS_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String ARGUMENTS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private static final String getRistoranti = "SELECT * FROM ristoranti";
	private static final String getClienti = "SELECT * FROM clienti";
	private static final String queryGetIndirizziByCliente = "SELECT * FROM indirizzi WHERE cliente_email = ?";
	private static final String getNumOrdineByRestaurant = "SELECT COUNT(num_ordine) FROM `ordini` o WHERE `o`.`ristorante_id` =? AND DATE(`o`.`data_ordine`) =?;";
	private static final String inserisciOrdine = "INSERT INTO ordini (`num_ordine`,`data_ordine`,`ristorante_id`,`cliente_email`,`destinazione`,`tipo`, `descrizione`, `stato`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String updateCodaOrdini = "UPDATE `ristoranti` SET `ordini_coda` = `ordini_coda` + 1 WHERE `id` = ?;";
	private static final String assegnaOrdine = "UPDATE `ordini` SET `stato` = ?, `persona_cf` = ?, `stima_orario` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?" ;
	
	
	
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
	
	/**
	 * Inserisce un ordine al Ristorante fatto dal relativo Cliente passato come parametro
	 * @param ordine
	 * @param ristorante
	 * @param cliente
	 * @return
	 * @throws Exception 
	 */
	public boolean inserisciOrdine(Ordine ordine, Ristorante ristorante, Cliente cliente) throws Exception {
		boolean queryRes = false;
		
		//Se gli ordini in coda hanno raggiunto la coda massima, impossibile inserire l' ordine
		if(ristorante.getOrdiniCoda() >= ristorante.getCodaMax())
			return queryRes;
		
		//Accediamo al db e otteniamo il numero ordine attuale
		Connection conn = openConnection();
		if(conn == null) return false;
		conn.setAutoCommit(false);
		try (PreparedStatement stmt = conn.prepareStatement(getNumOrdineByRestaurant)) {
			stmt.setInt(1, ristorante.getId());
			stmt.setObject(2, LocalDate.now());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				ordine.setNumOrdine(rs.getInt(1) + 1);
			} else {
				ordine.setNumOrdine(1);
			}
		} catch(Exception e) {
			conn.rollback();
			throw e;
		}
		
		//Inseriamo il nuovo ordine 
		if(ordine.getDataOrdine() == null)
			ordine.setDataOrdine(LocalDateTime.now());
		ordine.setStato("ORDINATO");
		ordine.setRistoranteId(ristorante.getId());
		ordine.setClienteEmail(cliente.getEmail());		
		try (PreparedStatement stmt = conn.prepareStatement(inserisciOrdine)) {
			stmt.setInt(1, ordine.getNumOrdine());
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));
			stmt.setInt(3, ordine.getRistoranteId());
			stmt.setString(4, ordine.getClienteEmail());
			stmt.setString(5, ordine.getDestinazione());
			stmt.setString(6, ordine.getTipo());
			stmt.setString(7, ordine.getDescrizione());
			stmt.setString(8, ordine.getStato());
			queryRes = stmt.executeUpdate() == 1;
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}
		
		//Se e' andato a buon fine, aggiorniamo la coda degli ordini del ristorante
		if(queryRes) {
			try (PreparedStatement stmt = conn.prepareStatement(updateCodaOrdini)) {
				stmt.setInt(1, ordine.getRistoranteId());
				queryRes = stmt.executeUpdate() == 1;
			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		}
		conn.commit();
		closeConnection(conn);
		return queryRes;
	}

	public boolean assegnaOrdine(Ordine ordine, Persona persona, LocalDateTime ora) throws Exception {
		boolean queryRes = false;
		Connection conn = openConnection();
		if(conn == null) return false;
		try(PreparedStatement stmt = conn.prepareStatement(assegnaOrdine)){
			stmt.setString(1, "ESPLETATO");
			stmt.setString(2, persona.getCf());
			stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
			stmt.setInt(4, ordine.getNumOrdine());
			stmt.setTimestamp(5, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));
			stmt.setInt(6, ordine.getRistoranteId());
			queryRes = stmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		closeConnection(conn);
		if(!queryRes)
			throw new IOException("Errore assegnazione ordine");
		return queryRes;
	}
	
	@Override
	public String getConnectionString() {
		return String.format("jdbc:mysql://%s:%d/%s%s", getServer(),getPort(),getDbName(),this.ARGUMENTS);
	}

	@Override
	public Connection openConnection(){
		Connection conn;
		try {
			Class.forName(DBMS_DRIVER);		
			conn = DriverManager.getConnection(getConnectionString(), getUsername(), getPassword());
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	public boolean closeConnection(Connection conn){
		try {
			if(!conn.isClosed()) {
				conn.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public ArrayList<Ristorante> getRistoranti() throws Exception {
		Connection conn = openConnection();
		if(conn == null) return null;
		ArrayList<Ristorante> ristoranti = new ArrayList<Ristorante>();
		try(Statement stmt = conn.createStatement()){
			ResultSet rs = stmt.executeQuery(getRistoranti);
			while(rs.next()) {
				Ristorante ristorante = new Ristorante(rs.getInt("id"), rs.getString("piva"), rs.getString("denominazione"), rs.getString("ragione_sociale"), rs.getString("tipologia"), rs.getInt("ordini_coda"), rs.getInt("coda_max"), rs.getString("telefono"), rs.getString("email"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"), rs.getString("citta"), rs.getString("provincia"));
				ristoranti.add(ristorante);
			}
		}
		return ristoranti;
	}

	@Override
	public ArrayList<Cliente> getClienti() throws Exception {
		Connection conn = openConnection();
		if(conn == null)
			return null;
		ArrayList<Cliente> clienti = new ArrayList<Cliente>();
		try(PreparedStatement stmt1 = conn.prepareStatement(getClienti)){
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {		
				Cliente cliente = new Cliente(rs.getString("email"),rs.getString("nome"),rs.getString("cognome"),rs.getString("telefono"),rs.getDate("data_reg").toLocalDate());
				for (Indirizzo indirizzo : this.getClienteIndirizzi(cliente)) {
					cliente.aggiungiIndirizzo(indirizzo);
				}
				clienti.add(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return clienti;
	}

	@Override
	public ArrayList<Indirizzo> getClienteIndirizzi(Cliente cliente) throws Exception {
		Connection conn = openConnection();
		if(conn == null)
			return null;
		ArrayList<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
		try(PreparedStatement stmt = conn.prepareStatement(queryGetIndirizziByCliente)){
			stmt.setString(1, cliente.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Indirizzo indirizzo = new Indirizzo(rs.getString("via"), rs.getString("civico"), rs.getString("cap"), rs.getString("citta"),rs.getString("provincia"));
				indirizzi.add(indirizzo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return indirizzi;
	}

}
