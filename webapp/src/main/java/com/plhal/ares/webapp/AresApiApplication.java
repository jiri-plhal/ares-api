package com.plhal.ares.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;

import java.net.URLClassLoader;
import java.util.Arrays;


@SpringBootApplication(scanBasePackages = "com.plhal.ares.models")
public class AresApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(AresApiApplication.class, args);
	}

}
