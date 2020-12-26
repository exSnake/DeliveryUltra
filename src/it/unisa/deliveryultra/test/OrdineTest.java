package it.unisa.deliveryultra.test;

import java.util.Random;

import it.unisa.deliveryultra.model.*;

public class OrdineTest {
	Random rnd;
	DBMysqlTest db;
	String[] tipo = {"PRIMO", "SECONDO", "MENU COMPLETO"};
	String[] descrizione = {
			"1xPIZZA MARGHERITA;1xCOCACOLA ZERO 330ml;2xACQUA NATUARALE 500ml", 
			"1xCARBONARA;1xFALANGHINA ROSSO calice;1xACQUA NATUARALE 500ml", 
			"2xPOLLO ALLO SPIEDO;2xCOCA COLA 330ml;1xPATATINE GRANDE",
			"2xPANINO HAMBURGER PATATINE PROVOLA;1xPANINO HAMBURGER INSALATA POMODORO BACON;2xCOCA COLA 330ml;1xPATATINE GRANDE"};
	
	public OrdineTest() {
		db = new DBMysqlTest();
		rnd = new Random();
	}
	
	public void run() {
		insertOrderShouldReturnTrue();
	}
	
	public void insertOrderShouldReturnTrue() {
		Cliente cliente = db.getRandomClienteFromDb();
		Ristorante ristorante = db.getRandomRistoranteFromDb();
		String destinazione = cliente.getIndirizzi().get(rnd.nextInt(cliente.getIndirizzi().size())).toString();
		String desc = descrizione[rnd.nextInt(descrizione.length)];
		String tip = tipo[rnd.nextInt(tipo.length)];
		Ordine ordine = new Ordine(0, null, ristorante.getId(), cliente.getEmail(), destinazione, tip, desc, null, null, null, null);
		try {
			db.inserisciOrdine(ordine, ristorante, cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
