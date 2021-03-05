package com.plhal.ares.webapp.controller;


import com.plhal.ares.dbLayer.Firma;

import com.plhal.ares.service.DataService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Controller - řídí celou aplikaci a na základě zadané URL provede potřebné úkony
@Controller
public class CompanyController {

    private final DataService dataService;

    public CompanyController(DataService dataService) {
        this.dataService = dataService;
    }

    // Nastaví mapování pro úvodní stránku
    @GetMapping("/")
    public String uvodniStranka() {

        return "uvod";
    }

    // Mapování pro stránku hledej
    // Pomocí anotace @RequestParam získáme IČO firmy, které uživatel vyplnil do
    // vyhledávacího pole
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

        dataService.pridejFirmu(comp);

        // Přidáme firmu do modelu, abychom k tomuto objektu měli přístup v html
        // dokumentu
        model.addAttribute("firma", comp);


        return "vypis-firmy";
    }

}
