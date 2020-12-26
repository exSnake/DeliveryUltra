package it.unisa.deliveryultra.model;

import java.time.LocalDateTime;

public class Ordine {
	private int numOrdine;
	private LocalDateTime dataOrdine;
	private int ristoranteId;
	private String clienteEmail;
	private String destinazione;
	private String tipo;
	private String descrizione;
	private String stato;
	private String stimaOrario;
	private String personaCf;
	private String orarioConsegna;

	public Ordine(int numOrdine, LocalDateTime dataOrdine, int ristoranteId, String clienteEmail, String destinazione,
			String tipo, String descrizione, String stato, String stimaOrario, String personaCf, String orarioConsegna) {
		super();
		this.numOrdine = numOrdine;
		this.dataOrdine = dataOrdine;
		this.ristoranteId = ristoranteId;
		this.clienteEmail = clienteEmail;
		this.destinazione = destinazione;
		this.tipo = tipo;
		this.setDescrizione(descrizione);
		this.stato = stato;
		this.stimaOrario = stimaOrario;
		this.personaCf = personaCf;
		this.orarioConsegna = orarioConsegna;
	}

	public Ordine() {
		
	}

	public int getNumOrdine() { return numOrdine; }
	public void setNumOrdine(int numOrdine) { this.numOrdine = numOrdine; }

	public LocalDateTime getDataOrdine() { return dataOrdine; }
	public void setDataOrdine(LocalDateTime dataOrdine) { this.dataOrdine = dataOrdine; }
	
	public int getRistoranteId() { return ristoranteId; }
	public void setRistoranteId(int ristoranteId) { this.ristoranteId = ristoranteId; }

	public String getClienteEmail() { return clienteEmail; }
	public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

	public String getDestinazione() { return destinazione; }
	public void setDestinazione(String destinazione) { this.destinazione = destinazione; }

	public String getTipo() { return tipo; }
	public void setTipo(String tipo) { this.tipo = tipo; }

	public String getStato() { return stato; }
	public void setStato(String stato) { this.stato = stato; }

	public String getStimaOrario() { return stimaOrario; }
	public void setStimaOrario(String stimaOrario) { this.stimaOrario = stimaOrario; }

	public String getPersonaCf() { return personaCf; }
	public void setPersonaCf(String personaCf) { this.personaCf = personaCf; }

	public String getOrarioConsegna() { return orarioConsegna; }
	public void setOrarioConsegna(String orarioConsegna) { this.orarioConsegna = orarioConsegna;}

	public String getDescrizione() { return descrizione; }
	public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
	
}
