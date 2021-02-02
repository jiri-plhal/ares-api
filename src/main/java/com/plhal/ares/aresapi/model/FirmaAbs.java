package com.plhal.ares.aresapi.model;

import java.util.List;

public abstract class FirmaAbs {

	private String nazevFirmy;

	private String pravniForma;

	private List<StatutarniOrgan> clenoveStatutranihoOrganu;
	
	private String sidlo;

	private List<String> predmetPodnikani;

	public String getNazevFirmy() {
		return nazevFirmy;
	}

	public void setNazevFirmy(String nazevFirmy) {
		this.nazevFirmy = nazevFirmy;
	}

	public String getPravniForma() {
		return pravniForma;
	}

	public void setPravniForma(String pravniForma) {
		this.pravniForma = pravniForma;
	}

	public List<StatutarniOrgan> getClenoveStatutranihoOrganu() {
		return clenoveStatutranihoOrganu;
	}

	public void setClenoveStatutranihoOrganu(List<StatutarniOrgan> clenoveStatutranihoOrganu) {
		this.clenoveStatutranihoOrganu = clenoveStatutranihoOrganu;
	}

	public String getSidlo() {
		return sidlo;
	}

	public void setSidlo(String sidlo) {
		this.sidlo = sidlo;
	}

	public List<String> getPredmetPodnikani() {
		return predmetPodnikani;
	}

	public void setPredmetPodnikani(List<String> predmetPodnikani) {
		this.predmetPodnikani = predmetPodnikani;
	}
	
	

}
