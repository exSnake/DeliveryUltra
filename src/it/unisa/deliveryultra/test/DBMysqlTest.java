package it.unisa.deliveryultra.test;

import java.sql.*;

import it.unisa.deliveryultra.model.*;

public class DBMysqlTest extends DBMysql {
	
	private static final String queryGetRandomCliente = "SELECT * FROM clienti ORDER BY RAND() LIMIT 1";
	private static final String queryGetIndirizziByCliente = "SELECT * FROM indirizzi WHERE cliente_email = ?";
	private static final String queryGetRandomRistorante = "SELECT * FROM ristoranti ORDER BY RAND() LIMIT 1";
	public DBMysqlTest() {
		super("127.0.0.1", 3306, "del2", "root", "root");
	}
	
	public Ristorante getRandomRistoranteFromDb() {
		Ristorante ristorante = null;
		Connection conn = openConnection();
		try(Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(queryGetRandomRistorante);
			if(rs.next()) {
				ristorante = new Ristorante(rs.getInt("id"), rs.getString("piva"), rs.getString("denominazione"), rs.getString("ragione_sociale"), rs.getString("tipologia"), rs.getInt("ordini_coda"), rs.getInt("coda_max"), rs.getString("telefono"), rs.getString("email"), rs.getString("via"), rs.getString("civico"), rs.getString("cap"), rs.getString("citta"), rs.getString("provincia"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		closeConnection(conn);
		return ristorante;
	}

	public Cliente getRandomClienteFromDb() {
		Cliente cliente = new Cliente();
		Connection conn = openConnection();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(queryGetRandomCliente);
			if (rs.next()) {
				cliente.setEmail(rs.getString("email"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCognome(rs.getString("cognome"));
				cliente.setTelefono(rs.getString("telefono"));
				cliente.setDataReg(rs.getDate("data_reg").toLocalDate());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try(PreparedStatement stmt = conn.prepareStatement(queryGetIndirizziByCliente)){
			stmt.setString(1, cliente.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Indirizzo indirizzo = new Indirizzo(rs.getString("via"), rs.getString("civico"), rs.getString("cap"), rs.getString("citta"),rs.getString("provincia"));
				cliente.aggiungiIndirizzo(indirizzo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeConnection(conn);
		return cliente;
	}


}
