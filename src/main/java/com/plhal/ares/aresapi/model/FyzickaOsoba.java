package com.plhal.ares.aresapi.model;

public class FyzickaOsoba implements StatutarniOrgan{

	private String jmeno;

	private String prijmeni;

	private String funkce;

	public FyzickaOsoba() {
	}

	public String getFunkce() {
		return funkce;
	}

	public void setFunkce(String funkce) {
		this.funkce = funkce;
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

	@Override
	public String toString() {
		return "FyzickaOsoba [jmeno=" + jmeno + ", prijmeni=" + prijmeni + "]";
	}

}
