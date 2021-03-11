package com.plhal.ares.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Concrete implementation of class for getting informations about requested company from Czech business register.
 */

@AllArgsConstructor
public class ParserRepositoryImpl implements ParserRepository {

    @NonNull
    private final ParserRepositoryProperties parserRepositoryProperties;

    /**
     * This method finds informations about company from Czech business register based on Identification number
     * of company. It conects to business register and parse XML document to get related data.
     *
     * @param ico Identification number of company (this number must have 8 digits).
     * @return Object of company with its informations. If company is not found or error happened, return value is null.
     */
    public Firma najdiFirmu(String ico) {

        // Objekt do kterého budu parsovat XML dokument
        Document doc;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {

            builder = factory.newDocumentBuilder();

            // Získávám XML dokument z URL adresy
            doc = builder.parse(parserRepositoryProperties.getUrlPrefix() + ico + parserRepositoryProperties.getUrlSufix());

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

        Firma firma = Firma.builder()
                .ico(ico)
                .clenoveStatutarnihoOrganu(pridejClenyStatutarnihoOrganu(doc))
                .zakladniKapital(zjistiZakladniKapital(doc))
                .nazevFirmy(zjistiNazev(doc))
                .sidlo(zjistiSidlo(doc))
                .predmetPodnikani(pridejPredmetyPodnikani(doc))
                .pravniForma(zjistiPravniFormu(doc))
                .build();
        for (StatutarniOrgan statOrgan : firma.getClenoveStatutarnihoOrganu()) {
            statOrgan.setFirma(firma);
        }

        return firma;

    }

    // Získávám údaje o členech SU
    private @NonNull List<StatutarniOrgan> pridejClenyStatutarnihoOrganu(@NonNull Document doc) {

        List<StatutarniOrgan> listStatutarniOrgan = new ArrayList<>();

        // Získávám kolekci členů statutárního orgánu
        NodeList tempNodeList = doc.getElementsByTagName("dtt:Clen_SO");
        Element tempE = (Element) tempNodeList.item(0);

        // Procházím kolekci statutárních orgánů společnosti a ukládám jejich údaje
        // (Jméno, příjmení a funkci) do pomocného objektu tempSO
        for (int i = 0; i < tempNodeList.getLength(); i++) {

            tempE = (Element) tempNodeList.item(i);

            // Testuji pro případ, že statutární orgán společnosti není fyzická osoba. V
            // případě, že to není fyzická osoba,
            // ve výpise se nezobrazí.
            if (!(tempE.getElementsByTagName("dtt:Jmeno").item(0) == null)) {

                StatutarniOrgan tempSO;

                String jmeno, prijmeni, funkce;
                // Nastavuji jméno, příjmení a funkci statutárního orgánu
                funkce = tempE.getElementsByTagName("dtt:Funkce").item(0).getTextContent();
                jmeno = tempE.getElementsByTagName("dtt:Jmeno").item(0).getTextContent().toLowerCase();
                prijmeni = tempE.getElementsByTagName("dtt:Prijmeni").item(0).getTextContent().toLowerCase();
                // První písmeno ve jméně a příjmení bude velké
                jmeno = jmeno.substring(0, 1).toUpperCase() + jmeno.substring(1);
                prijmeni = prijmeni.substring(0, 1).toUpperCase() + prijmeni.substring(1);

                tempSO = StatutarniOrgan.builder()
                        .prijmeni(prijmeni)
                        .jmeno(jmeno)
                        .funkce(funkce)
                        .build();

                // Přidávám člena statutárního orgánu do firmy
                listStatutarniOrgan.add(tempSO);

            }

        }

        return listStatutarniOrgan;
    }

    // Získávám údaje o základním kapitálu společnosti
    private @NonNull String zjistiZakladniKapital(@NonNull Document doc) {


        String zakladniKapital;

        NodeList tempNodeList = doc.getElementsByTagName("dtt:Kapital");

        // Zjišťuji, zda má firma základní kapitál
        if (tempNodeList.item(0) == null) {
            zakladniKapital = "Tato společnost nemá základní kapitál";
        } else {
            Element tempE = (Element) tempNodeList.item(0);

            // Vhodně formátuji hodnotu o základním kapitálu (Odstraňuji zbytečný znak a
            // přidávám "Kč" nakonec)
            zakladniKapital = tempE.getElementsByTagName("dtt:Kc").item(0).getTextContent();
            if (zakladniKapital.contains(";")) {
                zakladniKapital = zakladniKapital.substring(0, zakladniKapital.indexOf(";"));
            }
            zakladniKapital = zakladniKapital.concat(" Kč");

        }
        return zakladniKapital;
    }

    // Získávám údaje o názvu společnosti
    private @NonNull String zjistiNazev(@NonNull Document doc) {

        String nazev;

        NodeList tempNodeList = doc.getElementsByTagName("dtt:Obchodni_firma");
        nazev = tempNodeList.item(0).getTextContent();

        return nazev;

    }

    // Získávám údaje o sídle společnosti
    private @NonNull String zjistiSidlo(@NonNull Document doc) {

        NodeList tempNodeList = doc.getElementsByTagName("dtt:Sidlo");
        Element tempE = (Element) tempNodeList.item(0);

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
        return sidlo;

    }

    // Získávám údaje o předmětech podnikání
    private @NonNull List<PredmetPodnikani> pridejPredmetyPodnikani(@NonNull Document doc) {

        List<PredmetPodnikani> predmetPodnikani = new ArrayList<>();

        // Získávám kolekci předmětů podnikání
        NodeList tempNodeList = doc.getElementsByTagName("dtt:Predmet_podnikani");
        Element tempE = (Element) tempNodeList.item(0);

        // Testuji, zda firma má předmět podnikání
        if (tempE != null) {
            for (int i = 0; i < tempE.getElementsByTagName("dtt:Text").getLength(); i++) {

                // Přidávám předměty podnikání do kolekce
                String s = tempE.getElementsByTagName("dtt:Text").item(i).getTextContent();
                s = s.replaceAll("(\\n)", "");
                PredmetPodnikani p = PredmetPodnikani.builder().nazev(s).build();
                predmetPodnikani.add(p);
            }
        }
        return predmetPodnikani;
    }

    // Získávám údaje o právní formě podnikání
    private @NonNull String zjistiPravniFormu(@NonNull Document doc) {

        String pravniForma;

        NodeList tempNodeList = doc.getElementsByTagName("dtt:Zakladni_udaje");
        Element tempE = (Element) tempNodeList.item(0);
        pravniForma = tempE.getElementsByTagName("dtt:Nazev_PF").item(0).getTextContent();

        return pravniForma;
    }
}
