package com.plhal.ares.webapp.controller;


import com.plhal.ares.parser.Firma;

import com.plhal.ares.service.DataService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller of this application. This class sets mapping to requested urls.
 */
@Controller
public class CompanyController {

    private final DataService dataService;

    public CompanyController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * Sets mapping for homepage ("/")
     *
     * @return It returns template - "uvod.html"
     */
    @GetMapping("/")
    public String uvodniStranka() {

        return "uvod";
    }

    /**
     * Sets mapping for url ("/hledej"). This method finds company based on ico, which is in get method in parameter icoFirmy.
     *
     * @param icoFirmy Ico requested company.
     * @param model    Into this parameter we will save our company object and we will put it into template.
     * @return It returns template - "vypis-firmy.html"
     */
    @GetMapping("/hledej")
    public String company(@RequestParam("icoFirmy") String icoFirmy, Model model) {

        Firma comp;

        // Vyhledá firmu a přiřadí její údaje do objektu comp, pokud není firma
        // nenalezena bude v objektu comp null
        comp = dataService.najdiFirmu(icoFirmy);

        // Pokud je firma nenalezena, uživatele pošleme na stránku nenalezen.html
        if (comp == null) {
            return "nenalezen";
        }

        // Přidáme firmu do modelu, abychom k tomuto objektu měli přístup v html
        // dokumentu
        model.addAttribute("firma", comp);


        return "vypis-firmy";
    }

    /**
     * Sets mapping for url ("/firmapridana"). This method saves our company information into database.
     *
     * @param firma Our company object which will be save into database. This object is getting from post method.
     * @param model Into this parameter we will save if the company was successfully saved into database (true or false) and we will put it into template.
     * @return It returns template - "firma-pridana.html"
     */
    @PostMapping("/firmapridana")
    public String companyAdd(@ModelAttribute("firma") Firma firma, Model model) {
        if (dataService.findCompanyInDatabase(firma.getIco())) {
            model.addAttribute("inDatabase", true);
        } else {
            dataService.addCompany(firma);
            model.addAttribute("inDatabase", false);
        }
        return "firma-pridana";
    }

}
