package com.gdsc.solutionChallenge.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("v1.0") String appVersion) {

        Info info = new Info().title("LMS API Docs").version(appVersion)
                .description("2023 Solution Challenge API Docs")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("San Kim").url("https://github.com/kimtks456/").email("kimtks456@gmail.com"));
//                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
