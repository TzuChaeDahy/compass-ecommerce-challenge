package com.tzuchaedahy.compass_ecommerce_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class CompassEcommerceChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompassEcommerceChallengeApplication.class, args);
	}

}
