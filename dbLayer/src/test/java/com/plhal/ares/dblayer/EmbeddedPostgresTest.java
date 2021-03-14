package com.plhal.ares.dblayer;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Class for application testing.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = App.class)
@AutoConfigureEmbeddedDatabase(beanName = "dataSource")
public class EmbeddedPostgresTest {

    private FirmaRepository firmaRepository;

    private DataSource dataSource;

    @Autowired
    public EmbeddedPostgresTest (FirmaRepository firmaRepository, DataSource dataSource) {
        this.firmaRepository = firmaRepository;
        this.dataSource = dataSource;
    }

    @Test
    public void savingCompanyIntoDb() {

        String CompanyIco = "12345678";

        Firma companyToSave = Firma.builder()
                .ico(CompanyIco)
                .nazevFirmy("Nazev firmy")
                .pravniForma("s.r.o.")
                .sidlo("Brno")
                .zakladniKapital("2000000")
                .clenoveStatutarnihoOrganu(new ArrayList<>())
                .predmetPodnikani(new ArrayList<>())
                .build();

       firmaRepository.save(companyToSave);

       Optional<Firma> companySavedInDb = firmaRepository.findById(CompanyIco);

       Assertions.assertEquals(CompanyIco, companySavedInDb.get().getIco());
    }

}