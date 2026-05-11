package com.eventmanagement.config;

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
                .info(new Info()
                        .title("Event & Venue Management System API")
                        .version("1.0.0")
                        .description(
                                "Complete REST API for managing events, venues, registrations, tickets, and feedback.")
                        .contact(new Contact()
                                .name("Anubhav Jaiswal")
                                .url("https://anubhavjaiswal.tech")));
    }
}
