package com.plhal.ares.aresapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ControllerAdvice
@EnableWebMvc
public class ExceptionController {
	
	
	@ExceptionHandler
	public String handleError(HttpServletRequest req, Exception exception, Model model) {

		exception.printStackTrace(System.out);
		model.addAttribute("error", exception.getMessage() + ", kód chyby: " +  HttpStatus.BAD_REQUEST);
		return "error";
	}

}
