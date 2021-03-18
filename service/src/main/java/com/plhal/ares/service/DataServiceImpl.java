package com.plhal.ares.service;

import com.plhal.ares.dblayer.Firma;
import com.plhal.ares.parser.ParserRepository;
import com.plhal.ares.dblayer.FirmaRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Concrete implementation for service layer, which contains business logic for communication with model.
 */
@Slf4j
@AllArgsConstructor
public class DataServiceImpl implements DataService {

    @NonNull
    private final ParserRepository parserRepository;

    @NonNull
    private final FirmaRepository firmaRepository;

    /**
     * This method delegates finding company informations to model.
     *
     * @param ico Identification number of company.
     * @return Object of company with its informations. If company is not found or error happened, return value is null.
     */
    public Firma najdiFirmu(@NonNull String ico) {
        log.info("Looking for company wih ICO {} in Czech business register", ico);
        return parserRepository.najdiFirmu(ico);
    }

    /**
     * This method calls another method to save object into database.
     *
     * @param company Instance of Firma class, which will be save into database.
     */
    public void addCompany(@NonNull Firma company) {
        log.info("Saving company wih ICO {} into database", company.getIco());
        firmaRepository.save(company);
    }

    /**
     * This method finds company in database based on Ico.
     *
     * @param companyIco Ico of requested company
     * @return True, if requested company is in database.
     */
    public boolean findCompanyInDatabase(String companyIco) {
        log.info("Searching company with ICO {} in database", companyIco);
        return firmaRepository.existsById(companyIco);
    }
}
