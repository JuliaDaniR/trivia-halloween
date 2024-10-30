package com.halloween.trivia.springdoc;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Trivia Halloween")
                        .description("API Rest para desafío de Trivia sobre mitos y leyendas de Halloween")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@trivia"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://triviahalloween/api/licencia")));
    }

}
