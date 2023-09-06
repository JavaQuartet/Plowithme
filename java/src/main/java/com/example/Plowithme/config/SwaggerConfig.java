package com.example.Plowithme.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info =@Info(
                title = "Plowithme API",
                version = "${api.version}",
                contact = @Contact(
                        name = "Plowithme",  url = "http://3.39.75.222:8080"
                ),
                description = "${api.description}"
        )
)
@Configuration
public class SwaggerConfig {
}
