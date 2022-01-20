package com.ractoc.cookbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "OPTIONS", "GET", "DELETE", "PUT")
                        .allowedHeaders("Accept",
                                "Accept-Language",
                                "Content-Language",
                                "Content-Type",
                                "Last-Event-ID",
                                "DPR",
                                "Save-Data",
                                "Viewport-Width",
                                "Width",
                                "Authorization");
            }
        };
    }

}
