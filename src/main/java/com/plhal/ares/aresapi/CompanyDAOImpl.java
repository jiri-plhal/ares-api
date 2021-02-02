package com.plhal.ares.aresapi;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.plhal.ares.aresapi.model.Firma;
import com.plhal.ares.aresapi.model.FyzickaOsoba;
import com.plhal.ares.aresapi.model.StatutarniOrgan;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	private final String urlPreffix = "http://wwwinfo.mfcr.cz/cgi-bin/ares/darv_or.cgi?ico=";
	private final String urlSuffix = "&xml=0&ver=1.0.2";

	private Firma firma;

	public Firma najdiFirmu(String ICO) {

		firma = new Firma();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {

			builder = factory.newDocumentBuilder();

			// Získávám XML dokument z URL adresy ARES-API
			Document doc = builder.parse(urlPreffix + ICO + urlSuffix);

			// Kontroluji, zda daná firma existuje, pokud ne vracím null
			if (doc.getElementsByTagName("dtt:Error").getLength() > 0) {
				return null;
			}

			// Pomocné objekty pro parsování XML dokumentu
			NodeList tempNodeList;
			Element tempE;

			// Volám metodu pro získání základních informací o firmě
			this.pridejZakladniUdaje(doc);

			// Volám metodu pro získání členů statutárních orgánů
			this.pridejClenyStatutarnihoOrganu(doc);

			// Získávám údaje o základním kapitálu společnosti
			tempNodeList = doc.getElementsByTagName("dtt:Kapital");
			if (tempNodeList != null) {
				firma.setZakladniKapital("Tato společnost nemá základní kapitál");
			} else {
				tempE = (Element) tempNodeList.item(0);

				// Vhodně formátuji hodnotu o základním kapitálu
				String vklad = tempE.getElementsByTagName("dtt:Kc").item(0).getTextContent();
				if (vklad.contains(";")) {
					vklad = vklad.substring(0, vklad.indexOf(";"));
				}
				vklad = vklad.concat(" Kč");
				firma.setZakladniKapital(vklad);
			}

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return firma;

	}

	private void pridejZakladniUdaje(Document doc) {

		// Pomocné objekty pro parsování XML dokumentu
		NodeList tempNodeList;
		Element tempE;

		// Získávám název obchodní firmy
		tempNodeList = doc.getElementsByTagName("dtt:Obchodni_firma");
		firma.setNazevFirmy(tempNodeList.item(0).getTextContent());

		// Získávám údaje o sídle firmy
		tempNodeList = doc.getElementsByTagName("dtt:Sidlo");
		tempE = (Element) tempNodeList.item(0);

		// Získávám adresu sídla firmy a upravuju ji do vhodného formátu
		String sidlo = "";
		sidlo = sidlo.concat(tempE.getElementsByTagName("dtt:Nazev_ulice").item(0).getTextContent()).concat(" ");
		if (tempE.getElementsByTagName("dtt:Cislo_domovni").item(0) == null) {
			sidlo = sidlo.concat(tempE.getElementsByTagName("dtt:Cislo_do_adresy").item(0).getTextContent());
		} else {
			sidlo = sidlo.concat(tempE.getElementsByTagName("dtt:Cislo_domovni").item(0).getTextContent()).concat("/")
					.concat(tempE.getElementsByTagName("dtt:Cislo_orientacni").item(0).getTextContent());
		}

		sidlo = sidlo.concat(", ").concat(tempE.getElementsByTagName("dtt:Nazev_obce").item(0).getTextContent())
				.concat(", ").concat(tempE.getElementsByTagName("dtt:PSC").item(0).getTextContent()).concat(" ")
				.concat(tempE.getElementsByTagName("dtt:Nazev_obce").item(0).getTextContent());
		firma.setSidlo(sidlo);

		// Získávám kolekci předmětů podnikání
		tempNodeList = doc.getElementsByTagName("dtt:Predmet_podnikani");
		tempE = (Element) tempNodeList.item(0);
		for (int i = 0; i < tempE.getElementsByTagName("dtt:Text").getLength(); i++) {
			firma.getPredmetPodnikani().add(tempE.getElementsByTagName("dtt:Text").item(i).getTextContent());
		}

		// Získáme informace o právní formě společnosti
		tempNodeList = doc.getElementsByTagName("dtt:Zakladni_udaje");
		tempE = (Element) tempNodeList.item(0);
		firma.setPravniForma(tempE.getElementsByTagName("dtt:Nazev_PF").item(0).getTextContent());

	}

	private void pridejClenyStatutarnihoOrganu(Document doc) {

		StatutarniOrgan tempSO;

		// Získávám kolekci členů statutárního orgánu
		NodeList tempNodeList = doc.getElementsByTagName("dtt:Clen_SO");
		Element tempE = (Element) tempNodeList.item(0);

		if (tempE.getElementsByTagName("dtt:Obchodni_firma").item(0) == null) {

			for (int i = 0; i < tempNodeList.getLength(); i++) {
				tempSO = new FyzickaOsoba();
				tempE = (Element) tempNodeList.item(i);
				((FyzickaOsoba) tempSO).setFunkce(tempE.getElementsByTagName("dtt:Funkce").item(0).getTextContent());
				if (!(tempE.getElementsByTagName("dtt:Jmeno").item(0) == null)) {
					((FyzickaOsoba) tempSO).setJmeno(tempE.getElementsByTagName("dtt:Jmeno").item(0).getTextContent());
					((FyzickaOsoba) tempSO)
							.setPrijmeni(tempE.getElementsByTagName("dtt:Prijmeni").item(0).getTextContent());
					firma.getClenoveStatutarnihoOrganu().add(tempSO);
				}

			}
		} else {

			for (int i = 0; i < tempNodeList.getLength(); i++) {
				tempSO = new Firma();
				tempE = (Element) tempNodeList.item(i);
				((Firma) tempSO)
						.setNazevFirmy(tempE.getElementsByTagName("dtt:Obchodni_firma").item(0).getTextContent());
				firma.getClenoveStatutarnihoOrganu().add(tempSO);
			}
			

		}
		// Nastavuji údaje jednotlivých členů statutárních orgánů (z předchozí kolekce)

	}

}
