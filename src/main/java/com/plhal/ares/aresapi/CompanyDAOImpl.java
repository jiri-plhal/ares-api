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
import com.plhal.ares.aresapi.model.StatutarniOrgan;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

	// Preffix a Suffix URL adresy ze kterého získám XML dokument s informace z
	// obchodního rejstříku. Mezi preffixem a suffixem je identifikační číslo firmy
	private final String urlPreffix = "http://wwwinfo.mfcr.cz/cgi-bin/ares/darv_or.cgi?ico=";
	private final String urlSuffix = "&xml=0&ver=1.0.2";

	// Instance společnosti, kterou budu zjištovat a naplňovat daty
	private Firma firma;

	// Proměnná, ze které budu parsovat XML dokument
	Document doc;

	// Pomocné objekty pro parsování XML dokumentu
	NodeList tempNodeList;
	Element tempE;

	public Firma najdiFirmu(String ICO) {

		firma = new Firma();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {

			builder = factory.newDocumentBuilder();

			// Získávám XML dokument z URL adresy ARES-API
			doc = builder.parse(urlPreffix + ICO + urlSuffix);

			// Kontroluji, zda daná firma existuje, pokud ne vracím null
			if (doc.getElementsByTagName("dtt:Error").getLength() > 0) {
				return null;
			}

			// Volám metodu pro získání členů statutárních orgánů
			this.pridejClenyStatutarnihoOrganu();

			// Získávám údaje o základním kapitálu společnosti
			this.zjistiZakladniKapital();

			// Získávám údaje o názvu společnosti
			this.zjistiNazev();

			// Získávám údaje o sídle společnosti
			this.zjistiSidlo();

			// Získávám údaje o předmětech podnikání
			this.pridejPredmetyPodnikani();

			// Získávám údaje o právní formě podnikání
			this.zjistiPravniFormu();

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return firma;

	}

	private void pridejClenyStatutarnihoOrganu() {

		StatutarniOrgan tempSO;

		// Získávám kolekci členů statutárního orgánu
		NodeList tempNodeList = doc.getElementsByTagName("dtt:Clen_SO");
		Element tempE = (Element) tempNodeList.item(0);

		// Procházím kolekci statutárních orgánů společnosti a ukládám jejich údaje
		// (Jméno, příjmení a funkci) do instance objektu Firma
		for (int i = 0; i < tempNodeList.getLength(); i++) {
			tempSO = new StatutarniOrgan();
			tempE = (Element) tempNodeList.item(i);
			tempSO.setFunkce(tempE.getElementsByTagName("dtt:Funkce").item(0).getTextContent());
			if (!(tempE.getElementsByTagName("dtt:Jmeno").item(0) == null)) {
				tempSO.setJmeno(tempE.getElementsByTagName("dtt:Jmeno").item(0).getTextContent().toLowerCase());
				tempSO.setPrijmeni(tempE.getElementsByTagName("dtt:Prijmeni").item(0).getTextContent().toLowerCase());
				// První písmeno ve jméně bude velké 
				tempSO.setJmeno(tempSO.getJmeno().substring(0,1).toUpperCase() + tempSO.getJmeno().substring(1));
				tempSO.setPrijmeni(tempSO.getPrijmeni().substring(0,1).toUpperCase() + tempSO.getPrijmeni().substring(1));
				firma.getClenoveStatutarnihoOrganu().add(tempSO);
			}

		}

	}

	// Získávám údaje o základním kapitálu společnosti
	private void zjistiZakladniKapital() {
		// Získávám údaje o základním kapitálu společnosti
		tempNodeList = doc.getElementsByTagName("dtt:Kapital");

		// Zjišťuji, zda má firma základní kapitál
		if (tempNodeList.item(0) == null) {
			firma.setZakladniKapital("Tato společnost nemá základní kapitál");
		} else {
			tempE = (Element) tempNodeList.item(0);

			// Vhodně formátuji hodnotu o základním kapitálu (Odstraňuji zbytečný znak a
			// přidávám "Kč" nakonec)
			String vklad = tempE.getElementsByTagName("dtt:Kc").item(0).getTextContent();
			if (vklad.contains(";")) {
				vklad = vklad.substring(0, vklad.indexOf(";"));
			}
			vklad = vklad.concat(" Kč");
			firma.setZakladniKapital(vklad);
		}

	}

	// Získávám údaje o názvu společnosti
	private void zjistiNazev() {
		tempNodeList = doc.getElementsByTagName("dtt:Obchodni_firma");
		firma.setNazevFirmy(tempNodeList.item(0).getTextContent());

	}

	// Získávám údaje o sídle společnosti
	private void zjistiSidlo() {
		tempNodeList = doc.getElementsByTagName("dtt:Sidlo");
		tempE = (Element) tempNodeList.item(0);

	}

	// Získávám údaje o předmětech podnikání
	private void pridejPredmetyPodnikani() {

		// Získávám kolekci předmětů podnikání
		tempNodeList = doc.getElementsByTagName("dtt:Predmet_podnikani");
		tempE = (Element) tempNodeList.item(0);
		// Procházím kolekci předmětů podnikání
		for (int i = 0; i < tempE.getElementsByTagName("dtt:Text").getLength(); i++) {
			firma.getPredmetPodnikani().add(tempE.getElementsByTagName("dtt:Text").item(i).getTextContent());
		}

	}

	// Získávám údaje o právní formě podnikání
	private void zjistiPravniFormu() {
		tempNodeList = doc.getElementsByTagName("dtt:Zakladni_udaje");
		tempE = (Element) tempNodeList.item(0);
		firma.setPravniForma(tempE.getElementsByTagName("dtt:Nazev_PF").item(0).getTextContent());

	}
}
