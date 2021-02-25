package com.plhal.ares.service;

import com.plhal.ares.model.Firma;

/**
 * Interface for service layer, which contains business logic for communication with model.
 */
public interface DataService {

    /**
     * This method finds requested company informations.
     *
     * @param ico Identification number of company.
     * @return Object of company with its informations. If company is not found or error happened, return value is null.
     */
    public Firma najdiFirmu(String ico);

}
