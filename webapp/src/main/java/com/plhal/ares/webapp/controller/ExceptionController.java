package com.plhal.ares.webapp.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Controller pro mapování chyb
@ControllerAdvice
public class ExceptionController {

	// Vrací customer-error stránku v případě, že chybí request parametr "icoFirmy"
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String chybiParametrICO(MissingServletRequestParameterException ex, Model model) {
		String name = ex.getParameterName();
		model.addAttribute("error", "Chybí parametr: " + name);

		return "custom-error";
	}

}
