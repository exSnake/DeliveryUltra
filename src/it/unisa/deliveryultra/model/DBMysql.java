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
	private static final String accettaOrdine = "UPDATE `ordini` SET `stato` = ?, `stima_orario` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?;";
	private static final String ARGUMENTS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private static final String assegnaOrdine = "UPDATE `ordini` SET `stato` = ?, `persona_cf` = ?, `stima_orario` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?;";
	private static final String consegnaOrdine = "UPDATE `ordini` SET `stato` = ?, `orario_consegna` = ?, `nominativo_consegna` = ? WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?;";
	private static final String DBMS_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String decrementaCodaOrdini = "UPDATE `ristoranti` SET `ordini_coda` = `ordini_coda` - 1 WHERE `id` = ?;";
	private static final String eliminaOrdine = "DELETE FROM `ordini` WHERE `num_ordine` = ? AND `data_ordine` = ? AND `ristorante_id` = ?;";
	private static final String getClienti = "SELECT * FROM `clienti`;";
	private static final String getClientiConOrdiniEffettuati = "SELECT *, COUNT(cliente_email) as ordini_effettuati FROM clienti c JOIN ordini o ON c.email = o.cliente_email GROUP BY cliente_email";
	private static final String getClientiValutazioniBasse = "SELECT c.* FROM valutazioni v LEFT JOIN riders r ON r.persona_cf = v.rider_cf LEFT JOIN clienti c on v.cliente_email = c.email WHERE data_valutazione >= CURDATE() - INTERVAL + 7 DAY AND v.valutazione < r.score_medio;";
	private static final String getDeliveriesByRistorante = "SELECT * FROM deliveries WHERE ristorante_id = ?;";
	private static final String getDeliveryInterniByRistorante = "SELECT * FROM deliveries WHERE ristorante_id = ? AND tipologia = 'Interno'";
	
	private static final String getDipendentiByRistorante = "SELECT p.*, d2.* FROM deliveries d "
														+ "JOIN dipendenti d2 on d.codice = d2.delivery_codice "
														+ "JOIN persone p on p.cf = d2.persona_cf "
														+ "WHERE ristorante_id = ?;";
	private static final String getImpieghiByRider = "SELECT * FROM impieghi WHERE rider_persona_cf = ?";
	private static final String getNumOrdineByRestaurant = "SELECT COUNT(num_ordine) FROM `ordini` o WHERE `o`.`ristorante_id` =? AND DATE(`o`.`data_ordine`) =?;";
	private static final String getOrdini = "SELECT * FROM `ordini` ORDER BY `data_ordine` DESC;";
	private static final String getOrdiniByCliente = "SELECT * FROM ordini WHERE cliente_email = ?;";
	private static final String getOrdiniByRistorante = "SELECT * FROM `ordini` WHERE ristorante_id = ? ORDER BY `data_ordine` DESC;";
	private static final String getOrdiniConsegnatiDaRiderNonValutati = "SELECT * FROM ordini o LEFT JOIN riders r ON o.persona_cf = r.persona_cf WHERE r.num_valutazioni = 0;";
	
	private static final String getOrdiniEffettuatiByCliente = "SELECT COUNT(cliente_email) FROM ordini WHERE cliente_email = ? GROUP BY cliente_email";
	private static final String getOrdiniInCoda = "SELECT * FROM `ordini` WHERE `stato` <> 'CONSEGNATO' ORDER BY `data_ordine` DESC;";
	private static final String getOrdiniInCodaByRistorante = "SELECT * FROM ordini WHERE ristorante_id = ? AND `stato` <> 'CONSEGNATO' ORDER BY `data_ordine` DESC;";
	
	private static final String getPersoneByNominativoConsegnaUltimaSettimana = "SELECT DISTINCT p.nome, p.cognome, p.telefono FROM ordini o "
																+ "LEFT JOIN persone p on p.cf = o.persona_cf "
																+ "WHERE nominativo_consegna = ? AND data_ordine >= CURDATE() - INTERVAL + 7 DAY;";
	private static final String getRiders = "SELECT * FROM `riders` r LEFT JOIN `persone` p ON `r`.`persona_cf` = `p`.`cf`";
	private static final String getRidersByRistorante = "SELECT p.*, r.* FROM deliveries d "
														+ "JOIN affidi a on d.codice = a.delivery_codice "
														+ "    JOIN impieghi i on a.societa_piva = i.societa_piva "
														+ "        JOIN persone p on p.cf = i.rider_persona_cf "
														+ "            JOIN riders r on r.persona_cf = p.cf "
														+ "WHERE d.ristorante_id = ?;";
	private static final String getRidersValutabileByCliente = "SELECT * FROM riders r "
																+ "LEFT JOIN persone p on p.cf = r.persona_cf "
																+ "WHERE persona_cf NOT IN (SELECT v.rider_cf FROM valutazioni v WHERE v.cliente_email = ?) "
																+ "AND persona_cf IN (SELECT o.persona_cf FROM ordini o WHERE o.persona_cf = r.persona_cf AND cliente_email = ?);";
	private static final String getRistoranteDeliveryEsterni = "SELECT * FROM deliveries WHERE ristorante_id = ? AND tipologia = 'Esterno';";
	private static final String getRistoranteEs9 = "SELECT * FROM `ristoranti` WHERE `id` in ("
													+ "SELECT `ristorante_id` FROM `deliveries` "
													+ "LEFT JOIN `affidi` a on `deliveries`.`codice` = `a`.`delivery_codice` "
													+ "LEFT JOIN `societa` s on `a`.`societa_piva` = `s`.`piva` "
													+ "WHERE `tipologia` = \"Interno\" OR `s`.`denominazione` = \"Food Delivery\");";
	private static final String getRistoranti = "SELECT * FROM `ristoranti`;";
	
	//Seleziona tutti i ristoranti il cui orario di chiusura di oggi (prima join) sia maggiore dell'orario attuale e che abbiano almeno un delivery interno o esterno disponibile secondo la cadenza
	private static final String getRistorantiDisponibili = "SELECT r.* FROM ristoranti r JOIN ( "
																+ "    SELECT ristorante_id, (TIMESTAMP(CURDATE(),SUBSTR(replace(oa.ora, '-', ':'),7,5)) ) as chiusura FROM orari_apertura oa "
																+ "    WHERE oa.giorno = WEEKDAY(NOW())+1 "
																+ "    HAVING chiusura >= NOW() "
																+ "    ORDER BY oa.ristorante_id "
																+ ") tabOrario "
																+ "ON tabOrario.ristorante_id = r.id "
																+ "JOIN ( "
																+ "    SELECT * FROM deliveries "
																+ "    WHERE cadenza = 'Weekend' AND WEEKDAY(DATE_SUB(NOW(), INTERVAL 2 HOUR )) >= 4 AND WEEKDAY(DATE_SUB(NOW(), INTERVAL 2 HOUR )) <= 5 "
																+ "    UNION "
																+ "    SELECT * FROM deliveries "
																+ "    WHERE cadenza = 'Sabato' AND WEEKDAY(DATE_SUB(NOW(), INTERVAL 2 HOUR )) = 5 "
																+ "    UNION "
																+ "    SELECT * FROM deliveries "
																+ "    WHERE cadenza = 'Settimanale') tab1 "
																+ "ON tab1.ristorante_id = r.id "
																+ "WHERE r.ordini_coda < r.coda_max "
																+ "GROUP BY r.id; ";
	private  static final String getSocietaNonAssociateAlRistorante = "SELECT * FROM societa WHERE piva NOT IN (\r\n"
																		+ "    SELECT piva FROM deliveries "
																		+ "    JOIN affidi a on deliveries.codice = a.delivery_codice "
																		+ "    JOIN societa s on a.societa_piva = s.piva "
																		+ "    WHERE ristorante_id = ? "
																		+ ")";
	private static final String getValutazioniBasseByRider = "SELECT * FROM valutazioni v "
																+ "LEFT JOIN riders r ON r.persona_cf = v.rider_cf "
																+ "WHERE rider_cf = ? AND data_valutazione >= CURDATE() - INTERVAL + 7 DAY "
																+ "AND v.valutazione < r.score_medio;";
	private static final String getValutazioniByCliente = "SELECT * FROM valutazioni WHERE cliente_email = ?;";
	private static final String getValutazioniByRider = "SELECT * FROM valutazioni WHERE rider_cf = ?";
	private static final String incrementaCodaOrdini = "UPDATE `ristoranti` SET `ordini_coda` = `ordini_coda` + 1 WHERE `id` = ?;";
	private static final String inserisciDeliveryInternoPerRistorante = "INSERT INTO deliveries (ristorante_id, tipologia, descrizione, data_inizio, cadenza) VALUES (?, ?, ?, ?, ?);";
	private static final String inserisciDipendente = "INSERT INTO `dipendenti` (persona_cf, stato, anni_esperienza, curriculum, delivery_codice) VALUES (?, ?, ?, ?, ?);";
	private static final String inserisciOrdine = "INSERT INTO ordini (`num_ordine`,`data_ordine`,`ristorante_id`,`cliente_email`,`destinazione`,`tipo`, `descrizione`, `stato`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String inserisciPersona = "INSERT INTO `persone` (nome, cognome, cf,email, telefono) VALUES (?, ?, ?, ?, ?);";
	//Come prima, controllando però il singolo ristorante
	private static final String isRistoranteDisponibile = "SELECT (r.ordini_coda < r.coda_max*COUNT(tabOrario.ristorante_id)*COUNT(tab1.codice)) as disponibile FROM ristoranti r LEFT JOIN ( "
																+ "    SELECT ristorante_id, (TIMESTAMP(CURDATE(),SUBSTR(replace(oa.ora, '-', ':'),7,5)) ) as chiusura FROM orari_apertura oa "
																+ "    WHERE oa.giorno = WEEKDAY(NOW())+1 AND ristorante_id = ? "
																+ "    HAVING chiusura >= NOW() "
																+ "    ORDER BY oa.ristorante_id "
																+ ") tabOrario "
																+ "ON tabOrario.ristorante_id = r.id "
																+ "LEFT JOIN ( "
																+ "    SELECT * FROM deliveries "
																+ "    WHERE ristorante_id = ? AND cadenza = 'Weekend' AND WEEKDAY(DATE_SUB(NOW(), INTERVAL 2 HOUR )) >= 4 AND WEEKDAY(DATE_SUB(NOW(), INTERVAL 2 HOUR )) <= 5 "
																+ "    UNION "
																+ "    SELECT * FROM deliveries "
																+ "    WHERE ristorante_id = ? AND cadenza = 'Sabato' AND WEEKDAY(DATE_SUB(NOW(), INTERVAL 2 HOUR )) = 5 "
																+ "    UNION "
																+ "    SELECT * FROM deliveries "
																+ "    WHERE ristorante_id = ? AND cadenza = 'Settimanale') tab1 "
																+ "ON tab1.ristorante_id = r.id "
																+ "WHERE r.id = ?;";
	private static final String queryGetIndirizziByCliente = "SELECT * FROM indirizzi WHERE cliente_email = ?;";
	
	private static final String setAffidoSocietaADeliveryEsterno = "INSERT INTO affidi (delivery_codice, societa_piva) VALUES (?, ?);";
	private static final String setNuovoRiderScoreMedio = "UPDATE riders SET score_medio = (score_medio * num_valutazioni + ?) / (num_valutazioni + 1), "
																		+ "num_valutazioni = num_valutazioni + 1 WHERE persona_cf = ?;";
	private static final String setValutazioneRider = "INSERT INTO `valutazioni` (`rider_cf`, `cliente_email`, `data_valutazione`, `valutazione`) VALUES (?, ?, ?, ?);";

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

	@Override
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		if (!queryRes)
			throw new IOException("Errore assegnazione ordine");
		return queryRes;
	}

	@Override
	public boolean assegnaSocietaADeliveryEsterno(Societa societa, Delivery deliveryEsterno) throws SQLException, IOException {
		boolean queryRes = false;
		Connection conn = openConnection();
		if(conn == null) return queryRes;
		try(PreparedStatement stmt = conn.prepareStatement(setAffidoSocietaADeliveryEsterno )){
			stmt.setInt(1, deliveryEsterno.getCodice());
			stmt.setString(2, societa.getPiva());
			queryRes = stmt.executeUpdate() == 1;
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return queryRes;
	}

	@Override
	public boolean isRistoranteDisponibile(Ristorante ristorante) throws SQLException {
		boolean queryRes = false;
		Connection conn = openConnection();
		try(PreparedStatement stmt = conn.prepareStatement(isRistoranteDisponibile)){
			stmt.setInt(1, ristorante.getId());
			stmt.setInt(2, ristorante.getId());
			stmt.setInt(3, ristorante.getId());
			stmt.setInt(4, ristorante.getId());
			stmt.setInt(5, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				queryRes = rs.getBoolean("disponibile");
		} catch (SQLException e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return queryRes;
	}

	@Override
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return indirizzi;
	}

	@Override
	public List<Cliente> getClienti() throws SQLException {
		List<Cliente> clienti = new ArrayList<>();
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
	public List<Cliente> getClientiConOrdiniEffettuati() throws SQLException {
		List<Cliente> clienti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return clienti;
		try (PreparedStatement stmt1 = conn.prepareStatement(getClientiConOrdiniEffettuati)) {
			ResultSet rs = stmt1.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getString("email"), rs.getString("nome"), rs.getString("cognome"),
						rs.getString("telefono"), rs.getDate("data_reg").toLocalDate(), rs.getInt("ordini_effettuati"));
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
	public List<Cliente> getClientiValutazioniBasse() throws SQLException {
		List<Cliente> clienti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return clienti;
		try (PreparedStatement stmt1 = conn.prepareStatement(getClientiValutazioniBasse)) {
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
	public List<Delivery> getDeliveriesByRistorante(Ristorante ristorante) throws SQLException {
		List<Delivery> deliveries = new ArrayList<Delivery>();
		Connection conn = openConnection();
		if(conn == null) return deliveries;
		try(PreparedStatement stmt = conn.prepareStatement(getDeliveriesByRistorante)) {
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Delivery del = new Delivery(rs.getInt("codice"), rs.getInt("ristorante_id"), rs.getString("tipologia"), rs.getString("descrizione"), rs.getDate("data_inizio").toLocalDate(), rs.getString("cadenza"));
				deliveries.add(del);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		return deliveries;
	}

	@Override
	public List<Delivery> getDeliveriesInterniByRistorante(Ristorante ristorante) throws SQLException {
		Connection conn = openConnection();
		List<Delivery> deliveries = new ArrayList<>();
		if(conn == null) return deliveries;
		try(PreparedStatement stmt = conn.prepareStatement(getDeliveryInterniByRistorante)) {
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Delivery del = new Delivery(rs.getInt("codice"), rs.getInt("ristorante_id"), rs.getString("tipologia"), rs.getString("descrizione"), rs.getDate("data_inizio").toLocalDate(), rs.getString("cadenza"));
				deliveries.add(del);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return deliveries;
	}

	@Override
	public List<Impiego> getImpieghiByRider(Rider rider) throws SQLException {
		List<Impiego> impieghi = new ArrayList<>();
		Connection conn = openConnection();
		if(conn == null) return impieghi;
		try(PreparedStatement stmt = conn.prepareStatement(getImpieghiByRider)){
			stmt.setString(1, rider.getCf());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Impiego imp = new Impiego(rs.getString("societa_piva"), rs.getString("rider_persona_cf"), rs.getDouble("quota_oraria"), rs.getDate("data_impiego").toLocalDate());
				impieghi.add(imp);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return impieghi;
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
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
			closeConnection(conn);
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return ordini;
	}

	@Override
	public int getOrdiniEffettuatiByCliente(Cliente cliente) throws SQLException {
		int ordini = 0;
		Connection conn = openConnection();
		if (conn == null) throw new SQLException("Connessione errore");
		try (PreparedStatement stmt = conn.prepareStatement(getOrdiniEffettuatiByCliente)) {
			stmt.setString(1, cliente.getEmail());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				ordini = rs.getInt(1);
			}
		} catch (SQLException e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return ordini;
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return persone;
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
			closeConnection(conn);
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return persone;
	}

	@Override
	public List<Rider> getRiders() throws SQLException {
		List<Rider> riders = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return riders;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getRiders);
			while (rs.next()) {
				Rider rider = new Rider(rs.getString("cf"), rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono"), rs.getString("email"), rs.getBoolean("disponibilita"), rs.getDouble("score_medio"), rs.getInt("num_valutazioni"), rs.getInt("num_impiego"), rs.getDate("data_primo_impiego").toLocalDate(), rs.getBoolean("automunito"), rs.getString("targa"), rs.getString("tipo_veicolo"));
				riders.add(rider);
			}
		} catch (SQLException e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return riders;
	}

	@Override
	public List<Rider> getRiderValutabiliByCliente(Cliente cliente) throws SQLException {
		List<Rider> riders = new ArrayList<>();
		Connection conn = openConnection();
		if(conn == null) return riders;
		try (PreparedStatement stmt = conn.prepareStatement(getRidersValutabileByCliente)) {
			stmt.setString(1, cliente.getEmail());
			stmt.setString(2, cliente.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Rider rider = new Rider(rs.getString("cf"), rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono"), rs.getString("email"), rs.getBoolean("disponibilita"), rs.getDouble("score_medio"), rs.getInt("num_valutazioni"), rs.getInt("num_impiego"), rs.getDate("data_primo_impiego").toLocalDate(), rs.getBoolean("automunito"), rs.getString("targa"), rs.getString("tipo_veicolo"));
				riders.add(rider);
			}
		} catch (SQLException e) {
			closeConnection(conn);
			e.printStackTrace();
			throw e;
		}
		closeConnection(conn);
		return riders;
	}

	@Override
	public List<Delivery> getRistoranteServiziEsterni(Ristorante ristorante) throws SQLException {
		List<Delivery> deliveries = new ArrayList<>();
		Connection conn = openConnection();
		if(conn == null)
			return deliveries;
		try(PreparedStatement stmt = conn.prepareStatement(getRistoranteDeliveryEsterni)) {
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Delivery del = new Delivery(rs.getInt("codice"),rs.getInt("ristorante_id"),rs.getString("tipologia"),rs.getString("descrizione"),rs.getDate("data_inizio").toLocalDate(),rs.getString("cadenza"));
				deliveries.add(del);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return deliveries;
	}

	@Override
	public List<Ristorante> getRistoranti() throws SQLException {
		List<Ristorante> ristoranti = new ArrayList<>();
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return ristoranti;
	}

	@Override
	public List<Ristorante> getRistorantiDisponibili() throws SQLException {
		//TODO: Verificare anche l'orario di apertura/chiusura
		List<Ristorante> ristoranti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ristoranti;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getRistorantiDisponibili);
			while (rs.next()) {
				Ristorante ristorante = new Ristorante(rs.getInt("id"), rs.getString("piva"),
						rs.getString("denominazione"), rs.getString("ragione_sociale"), rs.getString("tipologia"),
						rs.getInt("ordini_coda"), rs.getInt("coda_max"), rs.getString("telefono"),
						rs.getString("email"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"),
						rs.getString("citta"), rs.getString("provincia"));
				ristoranti.add(ristorante);
			}
		} catch (SQLException e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return ristoranti;
	}

	@Override
	public List<Ristorante> getRistorantiEs9() throws SQLException {
		List<Ristorante> ristoranti = new ArrayList<>();
		Connection conn = openConnection();
		if (conn == null) return ristoranti;
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(getRistoranteEs9);
			while (rs.next()) {
				Ristorante ristorante = new Ristorante(rs.getInt("id"), rs.getString("piva"),
						rs.getString("denominazione"), rs.getString("ragione_sociale"), rs.getString("tipologia"),
						rs.getInt("ordini_coda"), rs.getInt("coda_max"), rs.getString("telefono"),
						rs.getString("email"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"),
						rs.getString("citta"), rs.getString("provincia"));
				ristoranti.add(ristorante);
			}
		} catch (SQLException e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return ristoranti;
	}

	@Override
	public List<Societa> getSocietaNonAssociateAlRistorante(Ristorante ristorante) throws SQLException {
		List<Societa> societa = new ArrayList<>();
		Connection conn = openConnection();
		if(conn == null) return societa;
		try (PreparedStatement stmt = conn.prepareStatement(getSocietaNonAssociateAlRistorante)){
			stmt.setInt(1, ristorante.getId());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Societa soc = new Societa(rs.getString("piva"), rs.getString("cf"), rs.getString("email"), rs.getString("denominazione"), rs.getString("amministratore"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"), rs.getString("citta"), rs.getString("provincia"));
				societa.add(soc);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return societa;
	}

	@Override
	public List<Valutazione> getValutazioniBasseByRider(Rider rider) throws SQLException {
		List<Valutazione> valutazioni = new ArrayList<>();
		Connection conn = openConnection();
		if(conn == null) return valutazioni;
		try(PreparedStatement stmt = conn.prepareStatement(getValutazioniBasseByRider)){
			stmt.setString(1, rider.getCf());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Valutazione val = new Valutazione(rs.getString("rider_cf"), rs.getString("cliente_email"), rs.getDate("data_valutazione").toLocalDate(),rs.getInt("valutazione"));
				valutazioni.add(val);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		return valutazioni;
	}

	@Override
	public List<Valutazione> getValutazioniByRider(Rider rider) throws SQLException {
		List<Valutazione> valutazioni = new ArrayList<>();
		Connection conn = openConnection();
		if(conn == null) return valutazioni;
		try(PreparedStatement stmt = conn.prepareStatement(getValutazioniByRider)){
			stmt.setString(1, rider.getCf());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Valutazione val = new Valutazione(rs.getString("rider_cf"), rs.getString("cliente_email"), rs.getDate("data_valutazione").toLocalDate(),rs.getInt("valutazione"));
				valutazioni.add(val);
			}
		} catch (Exception e) {
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return valutazioni;
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
			closeConnection(conn);
			throw e;
		}
		closeConnection(conn);
		return valutazioni;
	}

	@Override
	public boolean inserisciDeliveryInternoPerRistorante(Ristorante ristorante, Dipendente dipendente, String cadenza, String descrizione) throws SQLException, IOException {
		boolean queryRes = false;
		Connection conn = openConnection();
		if(conn == null) return queryRes;
		conn.setAutoCommit(false);
		try(PreparedStatement stmt = conn.prepareStatement(inserisciDeliveryInternoPerRistorante)){
			stmt.setInt(1, ristorante.getId());
			stmt.setString(2, "Interno");
			stmt.setString(3, descrizione);
			stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
			stmt.setString(5, cadenza);
			queryRes = stmt.executeUpdate() == 1;
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() as id;");
			if(rs.next() && queryRes)
				dipendente.setDeliveryCodice(rs.getInt("id"));
			else
				throw new IOException("Errore nell'inserimento del delivery");
		} catch (Exception e) {
			conn.rollback();
			closeConnection(conn);
			throw e;
		}
		queryRes = inserisciDipendente(dipendente, conn);
		if(!queryRes) {
			conn.rollback();
			closeConnection(conn);
			
		}
		conn.commit();
		closeConnection(conn);
		return queryRes;
	}

	private boolean inserisciDipendente(Dipendente dipendente, Connection conn) throws SQLException, IOException{
		boolean closeConn = false;
		boolean queryRes = false;
		if(conn == null || conn.isClosed()) {
			conn = openConnection();
			closeConn = true;
			if(conn == null)
				return queryRes;
			conn.setAutoCommit(false);
		}
		try(PreparedStatement stmt = conn.prepareStatement(inserisciPersona)) {
			stmt.setString(1,dipendente.getNome());
			stmt.setString(2,dipendente.getCognome());
			stmt.setString(3,dipendente.getCf());
			stmt.setString(4,dipendente.getEmail());
			stmt.setString(5,dipendente.getTelefono());
			queryRes = stmt.executeUpdate() == 1;
			if(!queryRes)
				throw new IOException("Errore nell'inserimento della persona");
		} catch (Exception e) {
			conn.rollback();
			if(closeConn)
				closeConnection(conn);
			throw e;
		}
		try(PreparedStatement stmt = conn.prepareStatement(inserisciDipendente)) {
			stmt.setString(1,dipendente.getCf());
			stmt.setString(2,dipendente.getStato());
			stmt.setInt(3,dipendente.getAnniEsperienza());
			stmt.setString(4,dipendente.getCurriculum());
			stmt.setInt(5,dipendente.getDeliveryCodice());
			queryRes = stmt.executeUpdate() == 1;
			if(!queryRes)
				throw new IOException("Errore nell'inserimento del dipendente");
		} catch (Exception e) {
			conn.rollback();
			if(closeConn)
				closeConnection(conn);
			throw e;
		}
		if(closeConn) {
			conn.commit();
			closeConnection(conn);
		}
		return queryRes;
	}

	@Override
	public boolean inserisciDipendenteADeliveryInterno(Dipendente dipendente, Delivery delivery)
			throws SQLException, IOException {
		dipendente.setDeliveryCodice(delivery.getCodice());
		return inserisciDipendente(dipendente, null);
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
	@Override
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
			closeConnection(conn);
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
			closeConnection(conn);
			throw e;
		}

		// Se e' andato a buon fine, aggiorniamo la coda degli ordini del ristorante
		if (queryRes) {
			try (PreparedStatement stmt = conn.prepareStatement(incrementaCodaOrdini)) {
				stmt.setInt(1, ordine.getRistoranteId());
				queryRes = stmt.executeUpdate() == 1;
			} catch (Exception e) {
				conn.rollback();
				closeConnection(conn);
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
	public boolean valutaRider(Cliente cliente, Rider rider, int valutazione) throws SQLException, IOException {
		boolean queryRes = false;
		Connection conn = openConnection();
		if(conn == null) return queryRes;
		conn.setAutoCommit(false);
		try (PreparedStatement stmt = conn.prepareStatement(setValutazioneRider)) {
			stmt.setString(1, rider.getCf());
			stmt.setString(2, cliente.getEmail());
			stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
			stmt.setInt(4, valutazione);
			queryRes = stmt.executeUpdate() == 1;
			if(!queryRes)
				throw new IOException("Errore nell'inserimento della valutazione");
		} catch (Exception e) {
			conn.rollback();
			closeConnection(conn);
			throw e;
		}
		try(PreparedStatement stmt = conn.prepareStatement(setNuovoRiderScoreMedio)){
			stmt.setInt(1, valutazione);
			stmt.setString(2, rider.getCf());
			queryRes = stmt.executeUpdate() == 1;
			if(!queryRes)
				throw new IOException("Errore nell'aggiornamento dello scoremedio");
		} catch (Exception e) {
			conn.rollback();
			closeConnection(conn);
			throw e;
		}
		conn.commit();
		closeConnection(conn);
		return queryRes;
	}

}
