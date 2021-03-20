package com.plhal.ares.webapi;

import com.plhal.ares.dblayer.Firma;
import com.plhal.ares.service.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for handling RESTful web services.
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/companies/")
public class WebapiController {

    DataService dataService;

    /**
     * Mapping for getting requested company from database. GET method is use.
     *
     * @param ico Requested company ICO.
     * @return If company is found in database, it will return that company. Otherwise return 404 status code.
     */
    @GetMapping("{ico}")
    public ResponseEntity<Firma> getCompany(@PathVariable String ico) {
        log.info("In RestController - getting informations about company with ico {}", ico);
        if (dataService.isCompanyInDatabase(ico)) {
            log.info("Company with ico {} is in database", ico);
            return new ResponseEntity<>(dataService.findCompanyInDatabase(ico), HttpStatus.OK);
        }
        log.warn("Company with ico {} was not found in database", ico);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Mapping for saving company into database. We can save only companies, that are not in database. POST method is use.
     *
     * @param company Firma object, that will be save into database.
     * @return If the saving is succesfull, it will return Firma object together with status code 200. If company is in database, 409 status code will be returned.
     */
    @PostMapping
    public ResponseEntity<Firma> saveCompany(@RequestBody Firma company) {
        log.info("In RestController - trying to save company with ico {} into database", company.getIco());
        if (dataService.isCompanyInDatabase(company.getIco())) {
            log.warn("Company is already in database, it is not possible to save it again.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(dataService.saveCompanyInDatabase(company), HttpStatus.CREATED);

    }

    /**
     * Mapping for getting all companies from database.
     *
     * @return Collection of all companies in database. If no companies are in database, null will be returned.
     */
    @GetMapping
    public ResponseEntity<List<Firma>> getCompanies() {
        log.info("In RestController - Showing all companies in database");
        return new ResponseEntity<>(dataService.showCompaniesInDatabase(), HttpStatus.OK);
    }

    /**
     * Mapping for removing company from database.
     *
     * @param ico ICO of company that will be deleted from database.
     * @return If company is found in database status code 200 will be returned. Otherwise 404 status code will be returned.
     */
    @DeleteMapping("{ico}")
    public ResponseEntity<Firma> deleteCompany(@PathVariable String ico) {
        log.info("In RestController - Trying to delete company with ico {} from database", ico);
        if (dataService.isCompanyInDatabase(ico)) {
            dataService.deleteCompany(ico);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.warn("Company is not in database. Deleting was unsuccesful");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Mapping for updating company in database. Only existing company can be updated.
     *
     * @param ico     ICO of company that we want to update.
     * @param company If company is in database, the new Firma object will be returned. If company is not found in database 404 status code will be returned. If ICO of updating company and ICO in Firma object in body doesnt match 409 status code will be returned.
     * @return
     */
    @PutMapping("{ico}")
    public ResponseEntity<Firma> updateCompany(@PathVariable String ico, @RequestBody Firma company) {
        log.info("In RestController - Trying to update company with ico {} in database", ico);
        if (!dataService.isCompanyInDatabase(ico)) {
            log.warn("Company is not in database, cant update non-existing company");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!ico.equals(company.getIco())) {
            log.warn("You cannot change ico company from {} to {}, cant perform updating", ico, company.getIco());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        dataService.deleteCompany(ico);
        return new ResponseEntity<>(dataService.saveCompanyInDatabase(company), HttpStatus.OK);
    }

    /**
     * Mapping from getting Firma object from Czech business register.
     *
     * @param ico ICO of requested company from Czech register.
     * @return If company is succesfully found in register it will return Firma object together with status code 200. 404 Status code will be returned in case, that requested company is not present in Czech register.
     */
    @GetMapping("fromregister/{ico}")
    public ResponseEntity<Firma> getCompanyFromRegister(@PathVariable String ico) {
        log.info("In RestController - trying to get informations about company with ico {} from register", ico);
        Firma companyFromRegister = dataService.najdiFirmu(ico);
        if (companyFromRegister == null) {
            log.warn("Company was not found in Czech business register!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(companyFromRegister, HttpStatus.OK);

    }

}
