package com.plhal.ares.webapp;

import com.plhal.ares.model.AresApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Import(AresApiConfig.class)
@SpringBootApplication
@EnableConfigurationProperties(AresApiProperties.class)
public class AresApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresApiApplication.class, args);
    }

}
