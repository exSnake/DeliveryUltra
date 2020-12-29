package it.unisa.deliveryultra.model;

public class Dipendente extends Persona {
	private String stato;
	private int anniEsperienza;
	private String curriculum;
	private int deliveryCodice;
	
	public Dipendente(String cf, String nome, String cognome, String telefono, String email, String stato,
			int anniEsperienza, String curriculum, int deliveryCodice) {
		super(cf, nome, cognome, telefono, email);
		this.stato = stato;
		this.anniEsperienza = anniEsperienza;
		this.curriculum = curriculum;
		this.deliveryCodice = deliveryCodice;
	}

	public Dipendente(String cf, String nome, String cognome, String telefono, String email, String stato,
			int anniEsperienza, String curriculum) {
		super(cf, nome, cognome, telefono, email);
		this.stato = stato;
		this.anniEsperienza = anniEsperienza;
		this.curriculum = curriculum;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public int getAnniEsperienza() {
		return anniEsperienza;
	}

	public void setAnniEsperienza(int anniEsperienza) {
		this.anniEsperienza = anniEsperienza;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public int getDeliveryCodice() {
		return deliveryCodice;
	}

	public void setDeliveryCodice(int deliveryCodice) {
		this.deliveryCodice = deliveryCodice;
	}
	
	
}
