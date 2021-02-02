package com.plhal.ares.aresapi;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.plhal.ares.aresapi.model.Firma;

public interface DataService{
	
	public Firma najdiFirmu(String ICO);

}
