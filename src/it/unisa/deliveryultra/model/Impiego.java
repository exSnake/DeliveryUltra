package it.unisa.deliveryultra.model;

import java.time.LocalDate;

public class Impiego {
	private String piva;
	private String riderCf;
	private double quotaOraria;
	private LocalDate dataImpiego;
	
	public Impiego(String piva, String riderCf, double quotaOraria, LocalDate dataImpiego) {
		super();
		this.piva = piva;
		this.riderCf = riderCf;
		this.quotaOraria = quotaOraria;
		this.dataImpiego = dataImpiego;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getRiderCf() {
		return riderCf;
	}

	public void setRiderCf(String riderCf) {
		this.riderCf = riderCf;
	}

	public double getQuotaOraria() {
		return quotaOraria;
	}

	public void setQuotaOraria(double quotaOraria) {
		this.quotaOraria = quotaOraria;
	}

	public LocalDate getDataImpiego() {
		return dataImpiego;
	}

	public void setDataImpiego(LocalDate dataImpiego) {
		this.dataImpiego = dataImpiego;
	}
	
	
}
