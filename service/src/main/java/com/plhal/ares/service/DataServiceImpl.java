package com.plhal.ares.service;

import com.plhal.ares.dbLayer.Firma;
import com.plhal.ares.dbLayer.DataRepository;
import com.plhal.ares.dbLayer.FirmaRepository;

/**
 * Concrete implementation for service layer, which contains business logic for communication with model.
 */

public class DataServiceImpl implements DataService {

    private DataRepository dataRepository;

    private FirmaRepository firmaRepository;

    public DataServiceImpl(DataRepository dataRepository, FirmaRepository firmaRepository) {
        this.dataRepository = dataRepository;
        this.firmaRepository = firmaRepository;
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

    public void pridejFirmu(Firma firma) {
        firmaRepository.save(firma);
    }
}
