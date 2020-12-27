/**
 * 
 */
package it.unisa.deliveryultra.model;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author exSna
 *
 */
public class DBMysql extends Database {
	private static final String getRistoranti = "SELECT * FROM `ristoranti`";
	private static final String getClienti = "SELECT * FROM `clienti`";
	private static final String getOrdini = "SELECT * FROM `ordini` ORDER BY `data_ordine` DESC";
	private static final String getOrdiniInCoda = "SELECT * FROM `ordini` WHERE `stato` <> 'CONSEGNATO' ORDER BY `data_ordine` DESC";
	private static final String getOrdiniByRistorante = "SELECT * FROM ordini WHERE ristorante_id = ? ORDER BY `data_ordine` DESC";
	private static final String getOrdiniInCodaByRistorante = "SELECT * FROM ordini WHERE ristorante_id = ? AND `stato` <> 'CONSEGNATO' ORDER BY `data_ordine` DESC";
	private static final String getOrdiniByCliente = "SELECT * FROM ordini WHERE cliente_email = ?";
	private static final String getOrdiniConsegnatiDaRiderNonValutati = "SELECT * FROM ordini o WHERE o.persona_cf NOT IN (SELECT rider_cf FROM valutazioni) AND persona_cf IS NOT NULL;";
	
	private static final String getRistorantiDisponibiliByCoda = "SELECT * FROM ristoranti r WHERE r.ordini_coda < r.coda_max";
	private static final String getRistoranteDisponibileByCoda = "SELECT ordini_coda < ristoranti.coda_max as disponibile FROM ristoranti WHERE ristoranti.id = ?";
	
	private static final String getPersoneByNominativoConsegnaUltimaSettimana = "SELECT p.nome, p.cognome, p.telefono FROM ordini o "
																+ "LEFT JOIN persone p on p.cf = o.persona_cf "
																+ "WHERE nominativo_consegna = ? AND data_ordine >= CURDATE() - INTERVAL + 7 DAY;";
	
	private static final String getValutazioniByCliente = "SELECT * FROM valutazioni WHERE cliente_email = ?";
	private static final String getRidersByRistorante = "SELECT p.*, r.* FROM deliveries d "
														+ "JOIN affidi a on d.codice = a.delivery_codice "
														+ "    JOIN impieghi i on a.societa_piva = i.societa_piva "
														+ "        JOIN persone p on p.cf = i.rider_persona_cf "
														+ "            JOIN riders r on r.persona_cf = p.cf "
														+ "WHERE d.ristorante_id = ?";
	private static final String getDipendentiByRistorante = "SELECT p.*, d2.* FROM deliveries d "
														+ "JOIN dipendenti d2 on d.codice = d2.delivery_codice "
														+ "JOIN persone p on p.cf = d2.persona_cf "
														+ "WHERE ristorante_id = ?";
	private static final String accettaOrdine = "UPDATE `ordini` SET `stato` = ?, `stima_orario` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?";
	private static final String consegnaOrdine = "UPDATE `ordini` SET `stato` = ?, `orario_consegna` = ?, `nominativo_consegna` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?";
	private static final String inserisciOrdine = "INSERT INTO ordini (`num_ordine`,`data_ordine`,`ristorante_id`,`cliente_email`,`destinazione`,`tipo`, `descrizione`, `stato`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String eliminaOrdine = "DELETE FROM `ordini` WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?";
	private static final String queryGetIndirizziByCliente = "SELECT * FROM indirizzi WHERE cliente_email = ?";
	private static final String getNumOrdineByRestaurant = "SELECT COUNT(num_ordine) FROM `ordini` o WHERE `o`.`ristorante_id` =? AND DATE(`o`.`data_ordine`) =?;";
	private static final String incrementaCodaOrdini = "UPDATE `ristoranti` SET `ordini_coda` = `ordini_coda` + 1 WHERE `id` = ?;";
	private static final String decrementaCodaOrdini = "UPDATE `ristoranti` SET `ordini_coda` = `ordini_coda` - 1 WHERE `id` = ?;";
	private static final String assegnaOrdine = "UPDATE `ordini` SET `stato` = ?, `persona_cf` = ?, `stima_orario` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?";
	private static final String DBMS_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String ARGUMENTS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

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
	public boolean accettaOrdine(Ordine ordine) throws SQLException,IOException {
		boolean queryRes = false;
		Connection conn = openConnection();
		if (conn == null) return false;
		try (PreparedStatement stmt = conn.prepareStatement(accettaOrdine)) {
			stmt.setString(1, "ORDINATO");
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
			stmt.setInt(3, ordine.getNumOrdine());
			stmt.setTimestamp(4, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));
			stmt.setInt(5, ordine.getRistoranteId());
			queryRes = stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		closeConnection(conn);
		if (!queryRes)
			throw new IOException("Errore assegnazione ordine");
		return queryRes;
	}

	public boolean assegnaOrdine(Ordine ordine, Persona persona, LocalDateTime ora) throws SQLException, IOException {
		boolean queryRes = false;
		Connection conn = openConnection();
		if (conn == null)
			return false;
		try (PreparedStatement stmt = conn.prepareStatement(assegnaOrdine)) {
			stmt.setString(1, "ESPLETATO");
			stmt.setString(2, persona.getCf());
			stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
			stmt.setInt(4, ordine.getNumOrdine());
			stmt.setTimestamp(5, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));
			stmt.setInt(6, ordine.getRistoranteId());
			queryRes = stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		closeConnection(conn);
		if (!queryRes)
			throw new IOException("Errore assegnazione ordine");
		return queryRes;
	}

	public boolean closeConnection(Connection conn) {
		try {
			if (!conn.isClosed()) {
				conn.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean consegnaOrdine(Ordine ordine, String nominativo) throws SQLException, IOException {
		boolean queryRes = false;
		Connection conn = openConnection();
		conn.setAutoCommit(false);
		try (PreparedStatement stmt = conn.prepareStatement(consegnaOrdine)) {
			stmt.setString(1, "CONSEGNATO");
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
			stmt.setString(3, nominativo);
			stmt.setInt(4, ordine.getNumOrdine());
			stmt.setTimestamp(5, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));
			stmt.setInt(6, ordine.getRistoranteId());
			queryRes = stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
		if (queryRes) {
			try (PreparedStatement stmt = conn.prepareStatement(decrementaCodaOrdini)) {
				stmt.setInt(1, ordine.getRistoranteId());
				queryRes = stmt.executeUpdate() == 1;
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
		//TODO: Impostare il rider/dipendente come disponibile
		conn.commit();
		closeConnection(conn);
		if (!queryRes)
			throw new IOException("Errore consegna ordine");
		return queryRes;
	}

	@Override
	public boolean eliminaOrdine(Ordine ordine) throws SQLException, IOException{
		boolean queryRes = false;
		Connection conn = openConnection();
		conn.setAutoCommit(false);
		try (PreparedStatement stmt = conn.prepareStatement(eliminaOrdine)) {
			stmt.setInt(1, ordine.getNumOrdine());
			stmt.setTimestamp(2, java.sql.Timestamp.valueOf(ordine.getDataOrdine()));
			stmt.setInt(3, ordine.getRistoranteId());
			queryRes = stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
		if (queryRes) {
			try (PreparedStatement stmt = conn.prepareStatement(decrementaCodaOrdini)) {
				stmt.setInt(1, ordine.getRistoranteId());
				queryRes = stmt.executeUpdate() == 1;
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		}
		conn.commit();
		closeConnection(conn);
		if (!queryRes)
			throw new IOException("Errore nell'eliminazione dell'ordine");
		return queryRes;
	}

	@Override
	public ArrayList<Indirizzo> getClienteIndirizzi(Cliente cliente) throws SQLException {
		ArrayList<Indirizzo> indirizzi = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return indirizzi;
		try (PreparedStatement stmt = conn.prepareStatement(queryGetIndirizziByCliente)) {
			stmt.setString(1, cliente.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Indirizzo indirizzo = new Indirizzo(rs.getString("via"), rs.getString("civico"), rs.getString("cap"),
						rs.getString("citta"), rs.getString("provincia"));
				indirizzi.add(indirizzo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return indirizzi;
	}
	
	@Override
	public ArrayList<Cliente> getClienti() throws SQLException {
		ArrayList<Cliente> clienti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return clienti;
		try (PreparedStatement stmt1 = conn.prepareStatement(getClienti)) {
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getString("email"), rs.getString("nome"), rs.getString("cognome"),
						rs.getString("telefono"), rs.getDate("data_reg").toLocalDate());
				for (Indirizzo indirizzo : this.getClienteIndirizzi(cliente)) {
					cliente.aggiungiIndirizzo(indirizzo);
				}
				clienti.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return clienti;
	}

	@Override
	public String getConnectionString() {
		return String.format("jdbc:mysql://%s:%d/%s%s", getServer(), getPort(), getDbName(), DBMysql.ARGUMENTS);
	}

	@Override
	public List<Ordine> getOrdini() throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ordini;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getOrdini);
			while (rs.next()) {
				var stimaOrario = rs.getTimestamp("stima_orario") == null ? null : rs.getTimestamp("stima_orario").toLocalDateTime();
				var personaCf = rs.getString("persona_cf");
				var orarioConsegna = rs.getTimestamp("orario_consegna") == null ? null : rs.getTimestamp("orario_consegna").toLocalDateTime();
				var nominativo = rs.getString("nominativo_consegna");
				Ordine ordine = new Ordine(rs.getInt("num_ordine"), rs.getTimestamp("data_ordine").toLocalDateTime(), rs.getInt("ristorante_id"), rs.getString("cliente_email"), rs.getString("destinazione"), rs.getString("tipo"), rs.getString("descrizione"), rs.getString("stato"), stimaOrario,personaCf,orarioConsegna,nominativo);
				ordini.add(ordine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ordini;
	}

	@Override
	public List<Ordine> getOrdiniByCliente(String email) throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ordini;
		try (PreparedStatement stmt = conn.prepareStatement(getOrdiniByCliente)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				var stimaOrario = rs.getTimestamp("stima_orario") == null ? null : rs.getTimestamp("stima_orario").toLocalDateTime();
				var personaCf = rs.getString("persona_cf");
				var orarioConsegna = rs.getTimestamp("orario_consegna") == null ? null : rs.getTimestamp("orario_consegna").toLocalDateTime();
				var nominativo = rs.getString("nominativo_consegna");
				Ordine ordine = new Ordine(rs.getInt("num_ordine"), rs.getTimestamp("data_ordine").toLocalDateTime(), rs.getInt("ristorante_id"), rs.getString("cliente_email"), rs.getString("destinazione"), rs.getString("tipo"), rs.getString("descrizione"), rs.getString("stato"), stimaOrario,personaCf,orarioConsegna,nominativo);
				ordini.add(ordine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ordini;
	}

	@Override
	public List<Ordine> getOrdiniByRistorante(Ristorante ristorante) throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ordini;
		try (PreparedStatement stmt = conn.prepareStatement(getOrdiniByRistorante)) {
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				var stimaOrario = rs.getTimestamp("stima_orario") == null ? null : rs.getTimestamp("stima_orario").toLocalDateTime();
				var personaCf = rs.getString("persona_cf");
				var orarioConsegna = rs.getTimestamp("orario_consegna") == null ? null : rs.getTimestamp("orario_consegna").toLocalDateTime();
				var nominativo = rs.getString("nominativo_consegna");
				Ordine ordine = new Ordine(rs.getInt("num_ordine"), rs.getTimestamp("data_ordine").toLocalDateTime(), rs.getInt("ristorante_id"), rs.getString("cliente_email"), rs.getString("destinazione"), rs.getString("tipo"), rs.getString("descrizione"), rs.getString("stato"), stimaOrario,personaCf,orarioConsegna,nominativo);
				ordini.add(ordine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ordini;
	}

	@Override
	public List<Ordine> getOrdiniConsegnatiDaRiderNonValutati() throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ordini;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getOrdiniConsegnatiDaRiderNonValutati);
			while (rs.next()) {
				var stimaOrario = rs.getTimestamp("stima_orario") == null ? null : rs.getTimestamp("stima_orario").toLocalDateTime();
				var personaCf = rs.getString("persona_cf");
				var orarioConsegna = rs.getTimestamp("orario_consegna") == null ? null : rs.getTimestamp("orario_consegna").toLocalDateTime();
				var nominativo = rs.getString("nominativo_consegna");
				Ordine ordine = new Ordine(rs.getInt("num_ordine"), rs.getTimestamp("data_ordine").toLocalDateTime(), rs.getInt("ristorante_id"), rs.getString("cliente_email"), rs.getString("destinazione"), rs.getString("tipo"), rs.getString("descrizione"), rs.getString("stato"), stimaOrario,personaCf,orarioConsegna,nominativo);
				ordini.add(ordine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ordini;
	}

	@Override
	public List<Ordine> getOrdiniInCoda() throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ordini;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getOrdiniInCoda);
			while (rs.next()) {
				var stimaOrario = rs.getTimestamp("stima_orario") == null ? null : rs.getTimestamp("stima_orario").toLocalDateTime();
				var personaCf = rs.getString("persona_cf");
				var orarioConsegna = rs.getTimestamp("orario_consegna") == null ? null : rs.getTimestamp("orario_consegna").toLocalDateTime();
				var nominativo = rs.getString("nominativo_consegna");
				Ordine ordine = new Ordine(rs.getInt("num_ordine"), rs.getTimestamp("data_ordine").toLocalDateTime(), rs.getInt("ristorante_id"), rs.getString("cliente_email"), rs.getString("destinazione"), rs.getString("tipo"), rs.getString("descrizione"), rs.getString("stato"), stimaOrario,personaCf,orarioConsegna,nominativo);
				ordini.add(ordine);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ordini;
	}

	@Override
	public List<Ordine> getOrdiniInCodaByRistorante(Ristorante ristorante) throws SQLException {
		List<Ordine> ordini = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ordini;
		try (PreparedStatement stmt = conn.prepareStatement(getOrdiniInCodaByRistorante)) {
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				var stimaOrario = rs.getTimestamp("stima_orario") == null ? null : rs.getTimestamp("stima_orario").toLocalDateTime();
				var personaCf = rs.getString("persona_cf");
				var orarioConsegna = rs.getTimestamp("orario_consegna") == null ? null : rs.getTimestamp("orario_consegna").toLocalDateTime();
				var nominativo = rs.getString("nominativo_consegna");
				Ordine ordine = new Ordine(rs.getInt("num_ordine"), rs.getTimestamp("data_ordine").toLocalDateTime(), rs.getInt("ristorante_id"), rs.getString("cliente_email"), rs.getString("destinazione"), rs.getString("tipo"), rs.getString("descrizione"), rs.getString("stato"), stimaOrario,personaCf,orarioConsegna,nominativo);
				ordini.add(ordine);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ordini;
	}

	@Override
	public List<Persona> getPersoneByRistoranteId(int ristoranteId) throws SQLException {
		List<Persona> persone = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return persone;
		try (PreparedStatement stmt = conn.prepareStatement(getDipendentiByRistorante)) {
			stmt.setInt(1, ristoranteId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Dipendente dipendente = new Dipendente(rs.getString("cf"), rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono"), rs.getString("email"), rs.getString("stato"), rs.getInt("anni_esperienza"), rs.getString("curriculum"), rs.getInt("delivery_codice"));
				persone.add(dipendente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		try (PreparedStatement stmt = conn.prepareStatement(getRidersByRistorante)) {
			stmt.setInt(1, ristoranteId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Rider rider = new Rider(rs.getString("cf"), rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono"), rs.getString("email"), rs.getBoolean("disponibilita"), rs.getDouble("score_medio"), rs.getInt("num_valutazioni"), rs.getInt("num_impiego"), rs.getDate("data_primo_impiego").toLocalDate(), rs.getBoolean("automunito"), rs.getString("targa"), rs.getString("tipo_veicolo"));
				persone.add(rider);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return persone;
	}

	@Override
	public ArrayList<Ristorante> getRistoranti() throws SQLException {
		ArrayList<Ristorante> ristoranti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ristoranti;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getRistoranti);
			while (rs.next()) {
				Ristorante ristorante = new Ristorante(rs.getInt("id"), rs.getString("piva"),
						rs.getString("denominazione"), rs.getString("ragione_sociale"), rs.getString("tipologia"),
						rs.getInt("ordini_coda"), rs.getInt("coda_max"), rs.getString("telefono"),
						rs.getString("email"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"),
						rs.getString("citta"), rs.getString("provincia"));
				ristoranti.add(ristorante);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ristoranti;
	}

	@Override
	public List<Valutazione> getValutazioniEffettuateByCliente(String email) throws SQLException {
		List<Valutazione> valutazioni = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return valutazioni;
		try (PreparedStatement stmt = conn.prepareStatement(getValutazioniByCliente)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Valutazione val = new Valutazione(rs.getString("rider_cf"), rs.getString("cliente_email"), rs.getDate("data_valutazione").toLocalDate(), rs.getInt("valutazione"));
				valutazioni.add(val);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return valutazioni;
	}

	/**
	 * Inserisce un ordine al Ristorante fatto dal relativo Cliente passato come
	 * parametro
	 * 
	 * @param ordine
	 * @param ristorante
	 * @param cliente
	 * @return
	 * @throws Exception
	 */
	public boolean inserisciOrdine(Ordine ordine, Ristorante ristorante, Cliente cliente) throws SQLException {
		boolean queryRes = false;

		// Se gli ordini in coda hanno raggiunto la coda massima, impossibile inserire l' ordine
		if (ristorante.getOrdiniCoda() >= ristorante.getCodaMax())
			return queryRes;

		// Accediamo al db e otteniamo il numero ordine attuale
		Connection conn = openConnection();
		if (conn == null)
			return false;
		conn.setAutoCommit(false);
		try (PreparedStatement stmt = conn.prepareStatement(getNumOrdineByRestaurant)) {
			stmt.setInt(1, ristorante.getId());
			stmt.setObject(2, LocalDate.now());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ordine.setNumOrdine(rs.getInt(1) + 1);
			} else {
				ordine.setNumOrdine(1);
			}
		} catch (Exception e) {
			conn.rollback();
			throw e;
		}

		// Inseriamo il nuovo ordine
		if (ordine.getDataOrdine() == null)
			ordine.setDataOrdine(LocalDateTime.now());
		ordine.setStato("IN ATTESA");
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

		// Se e' andato a buon fine, aggiorniamo la coda degli ordini del ristorante
		if (queryRes) {
			try (PreparedStatement stmt = conn.prepareStatement(incrementaCodaOrdini)) {
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

	@Override
	public Connection openConnection() {
		Connection conn;
		try {
			Class.forName(DBMS_DRIVER);
			conn = DriverManager.getConnection(getConnectionString(), getUsername(), getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean checkRistoranteDisponibile(Ristorante ristorante) throws SQLException {
		boolean queryRes = false;
		Connection conn = openConnection();
		try(PreparedStatement stmt = conn.prepareStatement(getRistoranteDisponibileByCoda)){
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				queryRes = rs.getBoolean("disponibile");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return queryRes;
	}

	@Override
	public List<Ristorante> getRistorantiDisponibili() throws SQLException {
		//TODO: Verificare anche l'orario di apertura/chiusura
		List<Ristorante> ristoranti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ristoranti;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getRistorantiDisponibiliByCoda);
			while (rs.next()) {
				Ristorante ristorante = new Ristorante(rs.getInt("id"), rs.getString("piva"),
						rs.getString("denominazione"), rs.getString("ragione_sociale"), rs.getString("tipologia"),
						rs.getInt("ordini_coda"), rs.getInt("coda_max"), rs.getString("telefono"),
						rs.getString("email"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"),
						rs.getString("citta"), rs.getString("provincia"));
				ristoranti.add(ristorante);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return ristoranti;
	}

	@Override
	public List<Persona> getPersoneByNominativoConsegna(String nominativo) throws SQLException {
		List<Persona> persone = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return persone;
		try (PreparedStatement stmt = conn.prepareStatement(getPersoneByNominativoConsegnaUltimaSettimana)) {
			stmt.setString(1, nominativo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Persona persona = new Persona();
				persona.setNome(rs.getString("nome"));
				persona.setCognome(rs.getString("cognome"));
				persone.add(persona);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return persone;
	}

}
