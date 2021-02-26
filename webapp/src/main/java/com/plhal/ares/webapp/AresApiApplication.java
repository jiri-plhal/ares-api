package com.plhal.ares.webapp;

import com.plhal.ares.model.DataRepository;
import com.plhal.ares.model.DataRepositoryImpl;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class AresApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresApiApplication.class, args);
    }

    @Bean
    public DataRepository dataRepository() {
        return new DataRepositoryImpl();
    }

    @Bean
    public DataService dataService() {
        return new DataServiceImpl(dataRepository());
    }

}
