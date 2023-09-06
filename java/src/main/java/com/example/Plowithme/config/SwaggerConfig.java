package com.example.Plowithme.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info =@Info(
                title = "Plowithme API",
                version = "${api.version}",
                contact = @Contact(
                        name = "Plowithme",  url = "http://43.200.172.177:8080"
                ),
                description = "${api.description}"
        )
)
@Configuration
public class SwaggerConfig {
}
