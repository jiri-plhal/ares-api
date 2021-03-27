package com.plhal.ares.service;

import java.util.List;

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
    public FirmaService najdiFirmu(String ico);

    /**
     * This method saves company into database.
     *
     * @param firma Instance of Firma class, which will be save into database.
     * @return Firma object which was saved into database.
     */
    public FirmaService saveCompanyInDatabase(FirmaService firma);

    /**
     * This method finds out, if a company is in database
     *
     * @param companyIco Ico of requested company
     * @return True, if requested company is in database.
     */
    public boolean isCompanyInDatabase(String companyIco);

    /**
     * This method finds a company in database based on Ico.
     *
     * @param companyIco Ico of requested company.
     * @return Requested Firma object. If company is not found, it will return null.
     */
    public FirmaService findCompanyInDatabase(String companyIco);


    /**
     * This method returns all companies in database.
     *
     * @return List<Firma> of all companies in database. If no company is present in database, null will be returned.
     */
    public List<FirmaService> showCompaniesInDatabase();

    /**
     * This method delete company from database.
     *
     * @param ico ICO of company, that we want to delete.
     */
    public void deleteCompany(String ico);

}
