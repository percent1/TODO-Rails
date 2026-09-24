package com.percy.todo_rails.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


@Bean
public OpenAPI todoRailsOpenAPI() {
    return new OpenAPI()
            .info(new Info()
                    .title("TODO-Rails API")
                    .version("1.0")
                    .description("RESTful Todo Management API built with Spring Boot.")
                    .contact(new Contact()
                            .name("Percy Ngobeni"))
                    .license(new License()
                            .name("Portfolio Project")));
}


}
