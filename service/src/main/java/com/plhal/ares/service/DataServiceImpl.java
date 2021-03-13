package com.plhal.ares.service;

import com.plhal.ares.parser.Firma;
import com.plhal.ares.parser.ParserRepository;
import com.plhal.ares.parser.FirmaRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * Concrete implementation for service layer, which contains business logic for communication with model.
 */
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

        return parserRepository.najdiFirmu(ico);
    }

    /**
     * This method calls another method to save object into database.
     *
     * @param company Instance of Firma class, which will be save into database.
     */
    public void addCompany(@NonNull Firma company) {
        firmaRepository.save(company);
    }

    /**
     * This method finds company in database based on Ico.
     *
     * @param companyIco Ico of requested company
     * @return True, if requested company is in database.
     */
    public boolean findCompanyInDatabase(String companyIco) {
        return firmaRepository.existsById(companyIco);
    }
}
