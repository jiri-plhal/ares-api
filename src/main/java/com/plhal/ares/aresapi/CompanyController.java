package com.plhal.ares.aresapi;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.plhal.ares.aresapi.model.Firma;

@Controller
public class CompanyController {

	@Autowired
	private DataService dataService;

	// Nastaví mapování pro úvodní stránku
	@GetMapping("/")
	public String uvodniStranka() {

		return "uvod";
	}

	// Mapování pro stránku hledej
	@GetMapping("/hledej")
	public String copmany(@RequestParam("icoFirmy") String icoFirmy, Model model) {

		Firma comp = null;

		//Vyhledá firmu a přiřadí její údaje do objektu comp, pokud není firma nenalezena bude v objektu comp null
		comp = dataService.najdiFirmu(icoFirmy);

		// Pokud je firma nenalezena, uživatele pošleme na stránku nenalezen.html
		if (comp == null) {
			return "nenalezen";
		}
		
		// Přidáme firmu do modelu, abychom mohli data zobrazit v HTML pomocí Thymeleaf
		model.addAttribute("firma", comp);

		return "vypis-firmy";
	}
}
