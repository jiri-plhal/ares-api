package com.plhal.ares.webapp;

import com.plhal.ares.parser.*;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import(DbConfig.class)
public class AresApiConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas("ares")
                .load();

        flyway.migrate();
        return flyway;
    }

    @Bean
    public ParserRepository dataRepository(ParserRepositoryProperties parserRepositoryProperties) {
        return new ParserRepositoryImpl(parserRepositoryProperties);
    }

    @Bean
    public DataService dataService(ParserRepository dataRepository, FirmaRepository firmaRepository) {

        return new DataServiceImpl(dataRepository, firmaRepository);

    }

    @Bean
    @ConfigurationProperties("ares-api-properties")
    public AresApiProperties aresApiProperties(ParserRepositoryProperties dataRepositoryProperties) {
        return new AresApiProperties(dataRepositoryProperties);
    }

    @Bean
    public ParserRepositoryProperties dataRepositoryProperties() {
        return new ParserRepositoryProperties();
    }

}
