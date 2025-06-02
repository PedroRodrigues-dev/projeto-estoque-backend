package com.estoque.sistemaestoque.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
    private String[] allowedOrigins;
    private Long maxAge = 3600L;
}