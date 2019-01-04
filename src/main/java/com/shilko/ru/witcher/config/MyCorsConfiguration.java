package com.shilko.ru.witcher.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyCorsConfiguration extends UrlBasedCorsConfigurationSource {
    MyCorsConfiguration() {
        final CorsConfiguration myConfiguration = new CorsConfiguration();
        myConfiguration.setAllowedOrigins(Stream.of("*").collect(Collectors.toList()));
        myConfiguration.setAllowedMethods(Stream.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .collect(Collectors.toList()));
        myConfiguration.setAllowCredentials(true);
        myConfiguration.setAllowedHeaders(Stream.of("Authorization", "Cache-Control", "Content-Type", "Role")
                .collect(Collectors.toList()));
        registerCorsConfiguration("/**", myConfiguration);
    }
}
