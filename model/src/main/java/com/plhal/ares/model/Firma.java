package com.plhal.ares.model;

import java.util.ArrayList;
import java.util.List;

// Třída Firma, do které ukládám všechny požadované informace a kterou poté předávám do modelu
public class Firma {

	private String nazevFirmy;

	private String pravniForma;

	private List<StatutarniOrgan> clenoveStatutarnihoOrganu;

	private String zakladniKapital;

	private String sidlo;

	private List<String> predmetPodnikani;

	public Firma() {
		clenoveStatutarnihoOrganu = new ArrayList<>();
		predmetPodnikani = new ArrayList<>();
	}

	public String getPravniForma() {
		return pravniForma;
	}

	public void setPravniForma(String pravniForma) {
		this.pravniForma = pravniForma;
	}

	public String getNazevFirmy() {
		return nazevFirmy;
	}

	public void setNazevFirmy(String nazevFirmy) {
		this.nazevFirmy = nazevFirmy;
	}

	public List<String> getPredmetPodnikani() {
		return predmetPodnikani;
	}

	public void setPredmetPodnikani(List<String> predmetPodnikani) {
		this.predmetPodnikani = predmetPodnikani;
	}

	public String getSidlo() {
		return sidlo;
	}

	public void setSidlo(String sidlo) {
		this.sidlo = sidlo;
	}

	public List<StatutarniOrgan> getClenoveStatutarnihoOrganu() {
		return clenoveStatutarnihoOrganu;
	}

	public void setClenoveStatutarnihoOrganu(List<StatutarniOrgan> jednatele) {
		this.clenoveStatutarnihoOrganu = jednatele;
	}

	public String getZakladniKapital() {
		return zakladniKapital;
	}

	public void setZakladniKapital(String zakladniKapital) {
		this.zakladniKapital = zakladniKapital;
	}

	@Override
	public String toString() {
		return "Firma [nazevFirmy=" + nazevFirmy + ", jednatele=" + clenoveStatutarnihoOrganu + ", zakladniKapital="
				+ zakladniKapital + "]";
	}

}
