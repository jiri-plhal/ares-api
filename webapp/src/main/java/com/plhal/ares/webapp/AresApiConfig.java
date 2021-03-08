package com.plhal.ares.webapp;

import com.plhal.ares.dblayer.*;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DbConfig.class)
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
