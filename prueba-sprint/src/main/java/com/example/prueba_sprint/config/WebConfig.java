package com.example.prueba_sprint.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow Ionic dev server (default 8100) and any localhost origin to access /api/**
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8100", "http://localhost:4200", "http://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        // Optionally allow the existing endpoints used by the app (if needed)
        registry.addMapping("/games/**")
                .allowedOrigins("http://localhost:8100", "http://localhost:4200", "http://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/anime/**")
                .allowedOrigins("http://localhost:8100", "http://localhost:4200", "http://localhost")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}