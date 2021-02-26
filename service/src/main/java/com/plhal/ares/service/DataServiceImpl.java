package com.plhal.ares.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plhal.ares.model.Firma;
import com.plhal.ares.model.DataRepository;

/**
 * Concrete implementation for service layer, which contains business logic for communication with model.
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;


    /**
     * This method delegates finding company informations to model.
     *
     * @param ico Identification number of company.
     * @return Object of company with its informations. If company is not found or error happened, return value is null.
     */
    public Firma najdiFirmu(String ico) {

        return dataRepository.najdiFirmu(ico);
    }

}
