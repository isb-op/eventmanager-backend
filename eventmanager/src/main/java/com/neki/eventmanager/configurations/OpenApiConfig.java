package com.neki.eventmanager.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Event Management API", version = "1.0", description = "Event Management API Documentation"))
public class OpenApiConfig {
	    @Bean
	    public OpenAPI myOpenApi() {
	        Contact contact = new Contact();
	        contact.setName("Isabella Pinheiro");
	        contact.setEmail("contact@eventmanager.com");
	        contact.setUrl("https://github.com/isb-op");

	        License apacheLicense = new License()
	            .name("Apache")
	            .url("https://opensource.org/licenses/Apache-2.0");
	        io.swagger.v3.oas.models.info.Info info = new io.swagger.v3.oas.models.info.Info()
	            .title("Event Manager")
	            .version("1.0")
	            .contact(contact)
	            .description("API for event management")
	            .termsOfService("https://github.com/isb-op/event-manager")
	            .license(apacheLicense);
	        return new OpenAPI().info(info);
	    }
	}