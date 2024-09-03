package com.tzuchaedahy.compass_ecommerce_challenge.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Compass E-commerce Challenge API", version = "1.0", description = "API documentation for the Compass E-commerce Challenge application", contact = @Contact(name = "Vinicius Alves Pacheco", email = "viniciusapacheco2004@gmail.com"), license = @License(name = "Apache 2.0", url = "http://springdoc.org")), servers = @Server(url = "http://localhost:8080"), security = @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth"))
@io.swagger.v3.oas.annotations.security.SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "Provide the JWT token with the Bearer prefix. Example: `Bearer {token}`")
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("bearerAuth",
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")
										.description("JWT token for authentication")))
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}
}