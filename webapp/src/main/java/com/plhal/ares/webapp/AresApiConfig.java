package com.plhal.ares.webapp;

import com.plhal.ares.model.AresApiProperties;
import com.plhal.ares.model.DataRepository;
import com.plhal.ares.model.DataRepositoryImpl;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AresApiConfig {


    @Bean
    public DataRepository dataRepository(AresApiProperties aresApiProperties) {
        return new DataRepositoryImpl(aresApiProperties);
    }

    @Bean
    public DataService dataService(DataRepository dataRepository) {

        return new DataServiceImpl(dataRepository);

    }

}
