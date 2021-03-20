package com.plhal.ares.webapi;

import com.plhal.ares.dblayer.DbConfig;
import com.plhal.ares.dblayer.FirmaRepository;
import com.plhal.ares.parser.ParserConfig;
import com.plhal.ares.parser.ParserRepository;
import com.plhal.ares.parser.ParserRepositoryProperties;
import com.plhal.ares.service.DataService;
import com.plhal.ares.service.DataServiceImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for webapi module. Here we define some beans.
 */
@Configuration
@Import({DbConfig.class, ParserConfig.class})
@EnableSwagger2
public class WebapiConfig {

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
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.plhal.ares.webapi"))
                .build();
    }


}
