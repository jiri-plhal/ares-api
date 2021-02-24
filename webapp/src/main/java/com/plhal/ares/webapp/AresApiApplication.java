package com.plhal.ares.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.plhal.ares.webapp", "com.plhal.ares.service", "com.plhal.ares.model"})
public class AresApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(AresApiApplication.class, args);
	}

}
