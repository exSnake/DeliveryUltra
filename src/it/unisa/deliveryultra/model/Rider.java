package it.unisa.deliveryultra.model;

import java.time.LocalDate;

public class Rider extends Persona {
	private boolean disponibilita;
	private double scoreMedio;
	private int numValutazioni;
	private int numImpiego;
	private LocalDate dataPrimoImpiego;
	private boolean autoMunito;
	private String targa;
	private String tipoVeicolo;
	
	public Rider(String cf, String nome, String cognome, String telefono, String email, boolean disponibilita,
			double scoreMedio, int numValutazioni, int numImpiego, LocalDate dataPrimoImpiego, boolean autoMunito,
			String targa, String tipoVeicolo) {
		super(cf, nome, cognome, telefono, email);
		this.disponibilita = disponibilita;
		this.scoreMedio = scoreMedio;
		this.numValutazioni = numValutazioni;
		this.numImpiego = numImpiego;
		this.dataPrimoImpiego = dataPrimoImpiego;
		this.autoMunito = autoMunito;
		this.targa = targa;
		this.tipoVeicolo = tipoVeicolo;
	}

	public boolean isDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}

	public double getScoreMedio() {
		return scoreMedio;
	}

	public void setScoreMedio(double scoreMedio) {
		this.scoreMedio = scoreMedio;
	}

	public int getNumValutazioni() {
		return numValutazioni;
	}

	public void setNumValutazioni(int numValutazioni) {
		this.numValutazioni = numValutazioni;
	}

	public int getNumImpiego() {
		return numImpiego;
	}

	public void setNumImpiego(int numImpiego) {
		this.numImpiego = numImpiego;
	}

	public LocalDate getDataPrimoImpiego() {
		return dataPrimoImpiego;
	}

	public void setDataPrimoImpiego(LocalDate dataPrimoImpiego) {
		this.dataPrimoImpiego = dataPrimoImpiego;
	}

	public boolean isAutoMunito() {
		return autoMunito;
	}

	public void setAutoMunito(boolean autoMunito) {
		this.autoMunito = autoMunito;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getTipoVeicolo() {
		return tipoVeicolo;
	}

	public void setTipoVeicolo(String tipoVeicolo) {
		this.tipoVeicolo = tipoVeicolo;
	}	
	
}
