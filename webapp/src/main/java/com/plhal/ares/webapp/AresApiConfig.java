package com.plhal.ares.webapp;

import com.plhal.ares.model.DataRepository;
import com.plhal.ares.model.DataRepositoryImpl;
import com.plhal.ares.model.DataRepositoryProperties;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AresApiConfig {


    @Bean
    public DataRepository dataRepository(DataRepositoryProperties dataRepositoryProperties) {
        return new DataRepositoryImpl(dataRepositoryProperties);
    }

    @Bean
    public DataService dataService(DataRepository dataRepository) {

        return new DataServiceImpl(dataRepository);

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
