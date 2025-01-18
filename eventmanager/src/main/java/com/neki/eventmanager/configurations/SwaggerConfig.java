package com.neki.eventmanager.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Event Management API",
        version = "1.0",
        description = "API for managing events"
))
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                .name("Isabella Pinheiro")
                .email("isabella.op95@gmail.com")
                .url("https://github.com/isb-op");
        License apacheLicense = new License()
                .name("Apache 2.0")
                .url("https://opensource.org/licenses/Apache-2.0");
        Components components = new Components()
                .addSecuritySchemes("JavaInUseSecurityScheme",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Event Manager")
                        .version("1.0")
                        .contact(contact)
                        .description("API for event management")
                        .termsOfService("https://github.com/isb-op/event-manager")
                        .license(apacheLicense))
                .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components(components);
    }
}
