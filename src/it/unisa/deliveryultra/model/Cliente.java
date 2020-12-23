package it.unisa.deliveryultra.model;

import java.util.ArrayList;

public class Cliente {
    private String email;
    private String nome;
    private String cognome;
    private String telefono;
    private String dataReg;
    private ArrayList<Indirizzo> indirizzi;
    
    // Costruttore clienti
    public Cliente(String email, String nome, String cognome, String telefono, String dataReg) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.dataReg = dataReg;
    }
    
	public Cliente(String email, String nome, String cognome, String telefono, String dataReg,
			ArrayList<Indirizzo> indirizzi) {
		super();
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.dataReg = dataReg;
		this.indirizzi = indirizzi;
	}

	public void aggiungiIndirizzo(Indirizzo indirizzo) {
    	this.indirizzi.add(indirizzo);
    }
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getDataReg() {
		return dataReg;
	}

	public void setDataReg(String dataReg) {
		this.dataReg = dataReg;
	}

	public ArrayList<Indirizzo> getIndirizzi() {
		return indirizzi;
	}

	public void setIndirizzi(ArrayList<Indirizzo> indirizzi) {
		this.indirizzi = indirizzi;
	}

	@Override
	public String toString() {
		return "Clienti [email=" + email + ", nome=" + nome + ", cognome=" + cognome + ", telefono=" + telefono
				+ ", dataReg=" + dataReg + ", indirizzi=" + indirizzi + "]";
	}
	
	
   
}
