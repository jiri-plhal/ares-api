package com.plhal.ares.service

import com.plhal.ares.dblayer.Firma
import com.plhal.ares.dblayer.FirmaRepository
import com.plhal.ares.dblayer.PredmetPodnikani
import com.plhal.ares.dblayer.StatutarniOrgan
import com.plhal.ares.parser.ParserRepository
import spock.lang.Specification

class ServiceSpec extends Specification {

    ParserRepository mockedParserRepository = Mock(ParserRepository)
    FirmaRepository mockedFirmaRepository = Mock(FirmaRepository)
    DataService dataServiceImpl = new DataServiceImpl(mockedParserRepository, mockedFirmaRepository)
    String icoFirmy = "12345678"
    Firma firma = Firma.builder()
            .ico(icoFirmy)
            .nazevFirmy("Název firmy")
            .clenoveStatutarnihoOrganu(Arrays.asList(StatutarniOrgan.builder().jmeno("Jméno").prijmeni("Příjmení").funkce("funkce").build()))
            .pravniForma("právní forma")
            .predmetPodnikani(Arrays.asList(PredmetPodnikani.builder().nazev("Předmět podnikání").build()))
            .sidlo("Sídlo firmy")
            .zakladniKapital("100000")
            .build()
    FirmaService firmaService = FirmaService.builder()
            .ico(icoFirmy)
            .nazevFirmy("Název firmy")
            .clenoveStatutarnihoOrganuService(Arrays.asList(StatutarniOrganService.builder().jmeno("Jméno").prijmeni("Příjmení").funkce("funkce").build()))
            .pravniForma("právní forma")
            .predmetPodnikaniService(Arrays.asList(PredmetPodnikaniService.builder().nazev("Předmět podnikání").build()))
            .sidlo("Sídlo firmy")
            .zakladniKapital("100000")
            .build()

    def "method test - najdiFirmu()"() {

        given:
        mockedParserRepository.najdiFirmu(_) >> firma

        when:
        FirmaService fService = dataServiceImpl.najdiFirmu(icoFirmy)
        String icoFinal = fService.ico

        then:
        icoFinal == icoFirmy
    }

    def "method test - saveCompanyInDatabase()"() {

        when:
        FirmaService firmaService1 = dataServiceImpl.saveCompanyInDatabase(firmaService)
        String icoFinal = firmaService1.ico

        then:
        icoFinal == icoFirmy
    }

    def "method test - isCompanyInDatase()"() {

        given:
        mockedFirmaRepository.existsById(_) >> false

        when:
        boolean isCompanyInDatabase = dataServiceImpl.isCompanyInDatabase(icoFirmy)

        then:
        isCompanyInDatabase == false
    }

    def "method test - findCompanyInDatabase()"() {

        given:
        mockedFirmaRepository.findById(_) >> Optional.of(firma)

        when:
        FirmaService firmaService1 = dataServiceImpl.findCompanyInDatabase(icoFirmy)
        String icoFinal = firmaService1.ico

        then:
        icoFinal == icoFirmy
    }

    def "method test - showCompaniesInDatabase()"() {

        given:
        mockedFirmaRepository.findAll() >> Arrays.asList(firma)

        when:
        List<FirmaService> firmaServiceList = dataServiceImpl.showCompaniesInDatabase()

        then:
        firmaServiceList != null
    }

    def "method test - deleteCompany()"() {

        when:
        dataServiceImpl.deleteCompany(icoFirmy)

        then:
        1 * mockedFirmaRepository.deleteById(icoFirmy)
    }
}