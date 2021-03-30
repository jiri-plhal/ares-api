package com.plhal.ares.service;

import com.plhal.ares.dblayer.Firma;
import com.plhal.ares.parser.ParserRepository;
import com.plhal.ares.dblayer.FirmaRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
    public FirmaService najdiFirmu(@NonNull String ico) {
        log.info("Looking for company wih ICO {} in Czech business register", ico);

        Firma firma = parserRepository.najdiFirmu(ico);

        return FirmaConvertor.fromDbmodelToService(firma);
    }

    /**
     * This method calls another method to save object into database.
     *
     * @param companyService Instance of Firma class, which will be save into database.
     * @return Firma object which was saved into database.
     */
    public FirmaService saveCompanyInDatabase(@NonNull FirmaService companyService) {
        log.info("Saving company wih ICO {} into database", companyService.getIco());

        Firma company = FirmaConvertor.fromServiceToDbmodel(companyService);

        firmaRepository.save(company);

        return FirmaConvertor.fromDbmodelToService(company);
    }

    /**
     * This method finds out, if a company is in database
     *
     * @param companyIco Ico of requested company
     * @return True, if requested company is in database.
     */
    public boolean isCompanyInDatabase(@NonNull String companyIco) {
        log.info("Is company with ICO {} in database?", companyIco);
        return firmaRepository.existsById(companyIco);

    }

    /**
     * This method finds a company in database based on Ico.
     *
     * @param companyIco Ico of requested company.
     * @return Requested Firma object. If company is not found, it will return null.
     */
    public FirmaService findCompanyInDatabase(@NonNull String companyIco) {
        log.info("Searching company with ICO {} in database", companyIco);
        return FirmaConvertor.fromDbmodelToService(firmaRepository.findById(companyIco).get());
    }

    /**
     * This method returns all companies in database.
     *
     * @return List<Firma> of all companies in database. If no company is present in database, null will be returned.
     */
    public List<FirmaService> showCompaniesInDatabase() {
        log.info("Showing all companies in database");
        List<FirmaService> allCompanies = new ArrayList<>();
        firmaRepository.findAll().forEach(x -> allCompanies.add(FirmaConvertor.fromDbmodelToService(x)));
        return allCompanies;
    }

    /**
     * This method delete company from database.
     *
     * @param ico ICO of company, that we want to delete.
     */
    public void deleteCompany(@NonNull String ico) {
        log.info("Deleting company with ico {} from database", ico);
        firmaRepository.deleteById(ico);
    }
}
