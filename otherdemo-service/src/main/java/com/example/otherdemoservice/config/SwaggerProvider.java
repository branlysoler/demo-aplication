package com.example.otherdemoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerProvider {

    @Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("DEMO API *BRANLY DEVELOPER*")
						.version("0.1")
						.description("DEMO API -- SPRINGBOOT 3 -- SWAGGER -- BRANLY"));
	}
    
}