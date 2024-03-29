package com.gdsc.solutionChallenge.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${jwt.header}")
    private String jwtHeader;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://friends-mentor.netlify.app", "http://localhost:3000")
                .allowedOriginPatterns("*")
                .exposedHeaders(jwtHeader)
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
