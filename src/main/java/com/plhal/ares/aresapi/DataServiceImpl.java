package com.plhal.ares.aresapi;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.plhal.ares.aresapi.model.Firma;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired
	private CompanyDAO companyDAO;

	public Firma najdiFirmu(String ICO) {
		
		return companyDAO.najdiFirmu(ICO);
		}	
		
	}


