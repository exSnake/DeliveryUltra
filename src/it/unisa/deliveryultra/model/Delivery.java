package it.unisa.deliveryultra.model;

import java.time.LocalDate;

public class Delivery {
	private int codice;
	private int ristoranteId;
	private String tipologia;
	private String descrizione;
	private LocalDate data;
	private String cadenza;
	
	public Delivery(int codice, int ristoranteId, String tipologia, String descrizione, LocalDate data,
			String cadenza) {
		this.codice = codice;
		this.ristoranteId = ristoranteId;
		this.tipologia = tipologia;
		this.descrizione = descrizione;
		this.data = data;
		this.cadenza = cadenza;
	}

	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public int getRistoranteId() {
		return ristoranteId;
	}

	public void setRistoranteId(int ristoranteId) {
		this.ristoranteId = ristoranteId;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getCadenza() {
		return cadenza;
	}

	public void setCadenza(String cadenza) {
		this.cadenza = cadenza;
	}

	@Override
	public String toString() {
		return codice + ") " + descrizione;
	}
	
	
	
}
