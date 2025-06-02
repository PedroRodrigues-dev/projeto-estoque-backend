package com.estoque.sistemaestoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.estoque.sistemaestoque.configs.CorsProperties;
import com.estoque.sistemaestoque.configs.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties({ JwtProperties.class, CorsProperties.class })
public class SistemaestoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaestoqueApplication.class, args);
	}

}
