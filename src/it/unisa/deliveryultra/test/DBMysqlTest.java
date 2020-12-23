package it.unisa.deliveryultra.test;
import it.unisa.deliveryultra.model.*;

public class DBMysqlTest extends DBMysql {

	public DBMysqlTest() {
		super("127.0.0.1", 3306, "del2", "root", "root");
	}

	public Cliente getRandomClienteFromDb() {
		return null;
	}

	public Ristorante getRandomRistoranteFromDb() {
		// TODO Auto-generated method stub
		return null;
	}

}
