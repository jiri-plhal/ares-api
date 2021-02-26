package com.plhal.ares.model;

// Třída statutárního orgánu (instance tohoto objektu jsou přidávány do třídy Firma)
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
