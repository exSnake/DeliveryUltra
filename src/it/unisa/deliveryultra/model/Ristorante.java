package it.unisa.deliveryultra.model;

public class Ristorante {
	private int id;
	private String piva;
	private String denominazione;
	private String ragioneSociale;
	private String tipologia;
	private int ordiniCoda;
	private int codaMax;
	private String telefono;
	private String email;
	private String via;
	private String civico;
	private String cap;
	private String citta;
	private String provincia;

	public Ristorante(int id, String piva, String denominazione, String ragioneSociale, String tipologia,
			int ordiniCoda, int codaMax, String telefono, String email, String via, String civico, String cap,
			String citta, String provincia) {
		this.id = id;
		this.piva = piva;
		this.denominazione = denominazione;
		this.ragioneSociale = ragioneSociale;
		this.tipologia = tipologia;
		this.ordiniCoda = ordiniCoda;
		this.codaMax = codaMax;
		this.telefono = telefono;
		this.email = email;
		this.via = via;
		this.civico = civico;
		this.cap = cap;
		this.citta = citta;
		this.provincia = provincia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public int getOrdiniCoda() {
		return ordiniCoda;
	}

	public void setOrdiniCoda(int ordiniCoda) {
		this.ordiniCoda = ordiniCoda;
	}

	public int getCodaMax() {
		return codaMax;
	}

	public void setCodaMax(int codaMax) {
		this.codaMax = codaMax;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return denominazione + ", " + citta + ", (" + provincia + ")";
	}

	public String getIndirizzoCompleto() {
		return String.format("%s, %s - %s %s (%s)", getVia(), getCivico(), getCap(), getCitta(), getProvincia());
	}
	
	

}
