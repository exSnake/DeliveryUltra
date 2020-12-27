package it.unisa.deliveryultra.model;

import java.time.LocalDate;

public class Valutazione {
	private String riderCf;
	private String clienteEmail;
	private LocalDate dataValutazione;
	private int val;
	
	public Valutazione(String riderCf, String clienteEmail, LocalDate dataValutazione, int valutazione) {
		this.riderCf = riderCf;
		this.clienteEmail = clienteEmail;
		this.dataValutazione = dataValutazione;
		this.val = valutazione;
	}

	public String getRiderCf() {
		return riderCf;
	}

	public void setRiderCf(String riderCf) {
		this.riderCf = riderCf;
	}

	public String getClienteEmail() {
		return clienteEmail;
	}

	public void setClienteEmail(String clienteEmail) {
		this.clienteEmail = clienteEmail;
	}

	public LocalDate getDataValutazione() {
		return dataValutazione;
	}

	public void setDataValutazione(LocalDate dataValutazione) {
		this.dataValutazione = dataValutazione;
	}

	public int getValutazione() {
		return val;
	}

	public void setValutazione(int valutazione) {
		this.val = valutazione;
	}
	
	
	
	
}
