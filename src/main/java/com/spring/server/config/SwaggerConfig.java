package com.spring.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Open-API documentation for Information Resource")
                                .version("6.6.6")
                                .contact(
                                        new Contact()
                                                .email("lildev1l.ya@gmail.com")
                                                .url("https://github.com/LilDev1l")
                                                .name("Max Yarmolik")
                                )
                );
    }
}