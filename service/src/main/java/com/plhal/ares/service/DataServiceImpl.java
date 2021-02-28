package com.plhal.ares.service;

import com.plhal.ares.model.Firma;
import com.plhal.ares.model.DataRepository;

/**
 * Concrete implementation for service layer, which contains business logic for communication with model.
 */

public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    public DataServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


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
