package com.plhal.ares.webapp;

import com.plhal.ares.dbLayer.DataRepository;
import com.plhal.ares.dbLayer.DataRepositoryImpl;
import com.plhal.ares.dbLayer.DataRepositoryProperties;
import com.plhal.ares.dbLayer.FirmaRepository;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.plhal.ares.dbLayer")
@EntityScan("com.plhal.ares.dbLayer")
public class AresApiConfig {


    @Bean
    public DataRepository dataRepository(DataRepositoryProperties dataRepositoryProperties) {
        return new DataRepositoryImpl(dataRepositoryProperties);
    }

    @Bean
    public DataService dataService(DataRepository dataRepository, FirmaRepository firmaRepository) {

        return new DataServiceImpl(dataRepository, firmaRepository);

    }

    @Bean
    @ConfigurationProperties("ares-api-properties")
    public AresApiProperties aresApiProperties(DataRepositoryProperties dataRepositoryProperties) {
        return new AresApiProperties(dataRepositoryProperties);
    }

    @Bean
    public DataRepositoryProperties dataRepositoryProperties() {
        return new DataRepositoryProperties();
    }

}
