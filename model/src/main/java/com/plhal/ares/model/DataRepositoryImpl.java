package com.plhal.ares.model;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Concrete implementation of class for getting informations about requested company from Czech business register.
 */

public class DataRepositoryImpl implements DataRepository {

    // Preffix a Suffix URL adresy ze kterého získám XML dokument s informace z
    // obchodního rejstříku. Mezi preffixem a suffixem je identifikační číslo firmy
    private final AresApiProperties aresApiProperties;

    // Instance společnosti, kterou budu zjištovat a naplňovat daty
    private Firma firma;

    // Objekt do kterého budu parsovat XML dokument
    private Document doc;

    // Pomocné objekty pro procházení XML dokumentu
    private NodeList tempNodeList;
    private Element tempE;

    public DataRepositoryImpl(AresApiProperties aresApiProperties) {
        this.aresApiProperties = aresApiProperties;
    }

    /**
     * This method finds informations about company from Czech business register based on Identification number
     * of company. It conects to business register and parse XML document to get related data.
     *
     * @param ico Identification number of company (this number must have 8 digits).
     * @return Object of company with its informations. If company is not found or error happened, return value is null.
     */
    public Firma najdiFirmu(String ico) {

        firma = new Firma();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {

            builder = factory.newDocumentBuilder();

            // Získávám XML dokument z URL adresy
            doc = builder.parse(aresApiProperties.getUrlPrefix() + ico + aresApiProperties.getUrlSufix());

        } catch (ParserConfigurationException e) {

            e.printStackTrace();
            return null;
        } catch (SAXException e) {

            e.printStackTrace();
            return null;
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

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

        return firma;

    }

    // Získávám údaje o členech SU
    private void pridejClenyStatutarnihoOrganu() {

        // Pomocný objekt pro uložení informací o statutárním orgánu společnosti.
        StatutarniOrgan tempSO;

        // Získávám kolekci členů statutárního orgánu
        NodeList tempNodeList = doc.getElementsByTagName("dtt:Clen_SO");
        Element tempE = (Element) tempNodeList.item(0);

        // Procházím kolekci statutárních orgánů společnosti a ukládám jejich údaje
        // (Jméno, příjmení a funkci) do pomocného objektu tempSO
        for (int i = 0; i < tempNodeList.getLength(); i++) {
            tempSO = new StatutarniOrgan();
            tempE = (Element) tempNodeList.item(i);

            // Testuji pro případ, že statutární orgán společnosti není fyzická osoba. V
            // případě, že to není fyzická osoba,
            // ve výpise se nezobrazí.
            if (!(tempE.getElementsByTagName("dtt:Jmeno").item(0) == null)) {

                // Nastavuji jméno, příjmení a funkci statutárního orgánu
                tempSO.setFunkce(tempE.getElementsByTagName("dtt:Funkce").item(0).getTextContent());
                tempSO.setJmeno(tempE.getElementsByTagName("dtt:Jmeno").item(0).getTextContent().toLowerCase());
                tempSO.setPrijmeni(tempE.getElementsByTagName("dtt:Prijmeni").item(0).getTextContent().toLowerCase());
                // První písmeno ve jméně a příjmení bude velké
                tempSO.setJmeno(tempSO.getJmeno().substring(0, 1).toUpperCase() + tempSO.getJmeno().substring(1));
                tempSO.setPrijmeni(
                        tempSO.getPrijmeni().substring(0, 1).toUpperCase() + tempSO.getPrijmeni().substring(1));

                // Přidávám člena statutárního orgánu do firmy
                firma.getClenoveStatutarnihoOrganu().add(tempSO);
            }

        }

    }

    // Získávám údaje o základním kapitálu společnosti
    private void zjistiZakladniKapital() {

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

        // Vhodně formátuji údaje z XML dokumentu (celá adresa bude jeden řetězec)
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

        // Nastavuji sídlo společnosti
        firma.setSidlo(sidlo);

    }

    // Získávám údaje o předmětech podnikání
    private void pridejPredmetyPodnikani() {

        // Získávám kolekci předmětů podnikání
        tempNodeList = doc.getElementsByTagName("dtt:Predmet_podnikani");
        tempE = (Element) tempNodeList.item(0);

        // Testuji, zda firma má předmět podnikání
        if (tempE == null) {
            firma.getPredmetPodnikani().add("Neexistuje předmět podnikání");
            return;
        }
        // Procházím kolekci předmětů podnikání
        for (int i = 0; i < tempE.getElementsByTagName("dtt:Text").getLength(); i++) {

            // Přidávám předměty podnikání do kolekce
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
