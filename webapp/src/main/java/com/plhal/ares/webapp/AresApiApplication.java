package com.plhal.ares.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AresApiConfig.class)
@SpringBootApplication
public class AresApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresApiApplication.class, args);
    }

}
