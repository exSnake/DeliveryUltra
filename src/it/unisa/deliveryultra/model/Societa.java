package it.unisa.deliveryultra.model;

/**
 * @author exSna
 *
 */
public class Societa {
	private String piva;
	private String cf;
	private String email;
	private String denominazione;
	private String amministratore;
	private String via;
	private String civico;
	private String cap;
	private String citta;
	private String provincia;
	
	public Societa(String piva, String cf, String email, String denominazione, String amministratore, String via,
			String civico, String cap, String citta, String provincia) {
		super();
		this.piva = piva;
		this.cf = cf;
		this.email = email;
		this.denominazione = denominazione;
		this.amministratore = amministratore;
		this.via = via;
		this.civico = civico;
		this.cap = cap;
		this.citta = citta;
		this.provincia = provincia;
	}
	public String getPiva() {
		return piva;
	}
	public void setPiva(String piva) {
		this.piva = piva;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getAmministratore() {
		return amministratore;
	}
	public void setAmministratore(String amministratore) {
		this.amministratore = amministratore;
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
		return piva + " - " + denominazione ;
	}
	
	
	
	
}
