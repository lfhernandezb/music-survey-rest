package com.example.musicstyle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MusicStyleConfig {

    // desde application.properties
    @Value("${cors.origin}")
    private String corsOrigin;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(corsOrigin);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("content-disposition");
        source.registerCorsConfiguration("/**", config);
        CorsFilter bean = new CorsFilter(source);
        //bean.setOrder(0);
        return bean;
    }

}
