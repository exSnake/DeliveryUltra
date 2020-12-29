package it.unisa.deliveryultra.model;

public class Affido {
	private int deliveryCodice;
	private String societaPiva;
	
	public Affido(int deliveryCodice, String societaPiva) {
		super();
		this.deliveryCodice = deliveryCodice;
		this.societaPiva = societaPiva;
	}
	public int getDeliveryCodice() {
		return deliveryCodice;
	}
	public void setDeliveryCodice(int deliveryCodice) {
		this.deliveryCodice = deliveryCodice;
	}
	public String getSocietaPiva() {
		return societaPiva;
	}
	public void setSocietaPiva(String societaPiva) {
		this.societaPiva = societaPiva;
	}
	
	
}
