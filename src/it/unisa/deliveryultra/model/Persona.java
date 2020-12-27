package it.unisa.deliveryultra.model;

public class Persona {
	private String cf;
	private String nome;
	private String cognome;
	private String telefono;
	private String email;
	
	public Persona(String cf, String nome, String cognome, String telefono, String email) {
		super();
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.email = email;
	}

	public Persona() {
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " - " + nome + " " + cognome;
	}
	
	
}
