package com.plhal.ares.aresapi.model;

// Třída statutárního orgánu, které potom přidávám do kolekce ve třídě Firma
public class StatutarniOrgan {

	private String jmeno;

	private String prijmeni;

	private String funkce;

	public StatutarniOrgan() {

	}

	public String getJmeno() {
		return jmeno;
	}

	public void setJmeno(String jmeno) {
		this.jmeno = jmeno;
	}

	public String getPrijmeni() {
		return prijmeni;
	}

	public void setPrijmeni(String prijmeni) {
		this.prijmeni = prijmeni;
	}

	public String getFunkce() {
		return funkce;
	}

	public void setFunkce(String funkce) {
		this.funkce = funkce;
	}

}
