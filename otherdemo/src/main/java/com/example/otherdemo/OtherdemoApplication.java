package com.example.otherdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class OtherdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtherdemoApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("OTHER DEMO API *BRANLY DEVELOPER*")
						.version("0.1")
						.description("OTHER DEMO API -- SPRINGBOOT 3 -- SWAGGER -- BRANLY"));
	}

}
