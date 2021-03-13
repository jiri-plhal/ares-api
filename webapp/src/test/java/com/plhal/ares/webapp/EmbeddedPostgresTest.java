package com.plhal.ares.webapp;

import com.plhal.ares.parser.Firma;
import com.plhal.ares.service.DataService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class for application testing.
 */
@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class EmbeddedPostgresTest {

    private final DataService dataService;

    @Autowired
    public EmbeddedPostgresTest(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * This method finds company in Czech business register, then saves it into database and check if the company is present in database.
     */
    @Test
    public void savingCompany() {

        final String companyTestIco = "04352246";

        Firma company = dataService.najdiFirmu(companyTestIco);

        dataService.addCompany(company);

        assertEquals(true, dataService.findCompanyInDatabase(companyTestIco));
    }

}