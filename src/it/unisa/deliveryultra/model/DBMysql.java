/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.sql.*;
import java.time.*;

/**
 * @author exSna
 *
 */
public class DBMysql extends Database {
	private final String DBMS_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String ARGUMENTS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private static final String getNumOrdineByRestaurant = "SELECT COUNT(num_ordine) FROM `ordini` o WHERE `o`.`ristorante_id` =? AND DATE(`o`.`data_ordine`) =?;";
	private static final String insertOrder = "INSERT INTO ordini (`num_ordine`,`data_ordine`,`ristorante_id`,`cliente_email`,`destinazione`,`tipo`, `descrizione`, `stato`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String updateCodaOrdini = "UPDATE `ristoranti` r1 SET `ordini_coda` = (SELECT `ordini_coda` FROM `ristoranti` r2 WHERE `r2`.`id` = ?) + 1 WHERE `r1`.`id` = ?;";
	
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
	public boolean insertOrder(Ordine ordine, Ristorante ristorante, Cliente cliente) throws Exception {
		boolean queryRes = false;
		
		//Se gli ordini in coda hanno raggiunto la coda massima, impossibile inserire l' ordine
		if(ristorante.getOrdiniCoda() >= ristorante.getCodaMax())
			return queryRes;
		
		//Accediamo al db e otteniamo il numero ordine attuale
		openConnection();
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
		ordine.setStato("ORDINATO");
		ordine.setRistoranteId(ristorante.getId());
		ordine.setClienteEmail(cliente.getEmail());		
		try (PreparedStatement stmt = conn.prepareStatement(insertOrder)) {
			stmt.setInt(1, ordine.getNumOrdine());
			stmt.setObject(2, LocalDate.now());
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
				stmt.setInt(2, ordine.getRistoranteId());
				queryRes = stmt.executeUpdate() == 1;
			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		}
		
		closeConnection();
		return queryRes;
	}


	@Override
	public String getConnectionString() {
		return String.format("jdbc:mysql://%s:%d/%s%s", getServer(),getPort(),getDbName(),this.ARGUMENTS);
	}

	@Override
	public boolean openConnection(){
		try {
			Class.forName(DBMS_DRIVER);		
			conn = DriverManager.getConnection(getConnectionString(), getUsername(), getPassword());
		} catch (ClassNotFoundException|SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean closeConnection(){
		try {
			if(!conn.isClosed()) {
				conn.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

}
