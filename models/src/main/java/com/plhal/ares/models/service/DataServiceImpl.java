package com.plhal.ares.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plhal.ares.models.model.Firma;
import com.plhal.ares.models.repository.DataRepository;

// Třída, určená pro manipulaci s daty
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private DataRepository dataRepository;

	// Volá metodu, která vrací hledanou firmu podle zadaného IČO od uživatele
	public Firma najdiFirmu(String ICO) {

		return dataRepository.najdiFirmu(ICO);
	}

}
