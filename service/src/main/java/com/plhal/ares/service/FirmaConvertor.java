package com.plhal.ares.service;

import com.plhal.ares.dblayer.Firma;
import com.plhal.ares.dblayer.PredmetPodnikani;
import com.plhal.ares.dblayer.StatutarniOrgan;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for converting objects from service model to database model and other way.
 */
@Slf4j
public class FirmaConvertor {

    /**
     * This method converts database Firma object into service Firma object.
     * @param company Firma object, which we want to convert into FirmaService object
     * @return Object of FirmaService corresponding to Firma class.
     */
    public static FirmaService fromDbmodelToService(Firma company) {
        log.info("Start converting company {} from database model to service model", company.getIco());
        List<PredmetPodnikaniService> predmetPodnikaniServiceList = new ArrayList<>();
        List<StatutarniOrganService> statutarniOrganServiceList = new ArrayList<>();

        FirmaService firmaService = FirmaService.builder()
                .ico(company.getIco())
                .nazevFirmy(company.getNazevFirmy())
                .pravniForma(company.getPravniForma())
                .sidlo(company.getSidlo())
                .zakladniKapital(company.getZakladniKapital())
                .predmetPodnikaniService(predmetPodnikaniServiceList)
                .clenoveStatutarnihoOrganuService(statutarniOrganServiceList)
                .build();
        log.info("FirmaService object of ico {} was succesfully created", company.getIco());

        if (company.getPredmetPodnikani() != null) {
            company.getPredmetPodnikani()
                    .forEach(x -> predmetPodnikaniServiceList.add(PredmetPodnikaniService.builder().firmaService(firmaService).nazev(x.getNazev()).build()));
        }
        if (company.getClenoveStatutarnihoOrganu() != null) {
            company.getClenoveStatutarnihoOrganu()
                    .forEach(x -> statutarniOrganServiceList.add(StatutarniOrganService.builder().firmaService(firmaService).funkce(x.getFunkce()).jmeno(x.getJmeno()).prijmeni(x.getPrijmeni()).build()));
        }

        firmaService.setClenoveStatutarnihoOrganuService(statutarniOrganServiceList);
        firmaService.setPredmetPodnikaniService(predmetPodnikaniServiceList);

        log.info("Converting company {} from database model to service model is done", company.getIco());

        return firmaService;

    }

    /**
     * This method converts service Firma object into database Firma object.
     * @param firmaService FirmaService object, which we want to convert into Service object
     * @return Object of Firma corresponding to FirmaService class.
     */
    public static Firma fromServiceToDbmodel(FirmaService firmaService) {
        log.info("Start converting company {} from service model to database model", firmaService.getIco());
        List<PredmetPodnikani> predmetPodnikaniList = new ArrayList<>();
        List<StatutarniOrgan> statutarniOrganList = new ArrayList<>();

        Firma firma = Firma.builder()
                .ico(firmaService.getIco())
                .nazevFirmy(firmaService.getNazevFirmy())
                .pravniForma(firmaService.getPravniForma())
                .sidlo(firmaService.getSidlo())
                .zakladniKapital(firmaService.getZakladniKapital())
                .predmetPodnikani(predmetPodnikaniList)
                .clenoveStatutarnihoOrganu(statutarniOrganList)
                .build();

        log.info("Firma object of ico {} was succesfully created", firmaService.getIco());

        if (firmaService.getPredmetPodnikaniService() != null) {
            firmaService.getPredmetPodnikaniService()
                    .forEach(x -> predmetPodnikaniList.add(PredmetPodnikani.builder().seznamFirem(Arrays.asList(firma)).nazev(x.getNazev()).build()));
        }
        if (firmaService.getClenoveStatutarnihoOrganuService() != null) {
            firmaService.getClenoveStatutarnihoOrganuService()
                    .forEach(x -> statutarniOrganList.add(StatutarniOrgan.builder().firma(firma).funkce(x.getFunkce()).jmeno(x.getJmeno()).prijmeni(x.getPrijmeni()).build()));
        }

        firma.setClenoveStatutarnihoOrganu(statutarniOrganList);
        firma.setPredmetPodnikani(predmetPodnikaniList);

        log.info("Converting company {} from service model to service database is done", firmaService.getIco());

        return firma;
    }
}
