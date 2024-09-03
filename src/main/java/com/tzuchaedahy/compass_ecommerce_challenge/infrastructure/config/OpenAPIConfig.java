package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI custom() {
		OpenAPI openAPI = new OpenAPI();

		openAPI
				.info(
						new Info().title("Compass - Desafio E-commerce")
								.version("1.0"))
				.components(
						new Components()
								.addSecuritySchemes(
										"JavaInUseSecurityScheme",
										new SecurityScheme().name(
												"JavaInUseSecurityScheme")
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.bearerFormat("JWT")));

		return openAPI;
	}
}
