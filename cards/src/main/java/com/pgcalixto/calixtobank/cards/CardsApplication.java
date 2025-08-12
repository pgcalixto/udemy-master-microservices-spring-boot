package com.pgcalixto.calixtobank.cards;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.pgcalixto.calixtobank.cards.controller") })
@EnableJpaRepositories("com.pgcalixto.calixtobank.cards.repository")
@EntityScan("com.pgcalixto.calixtobank.cards.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Cards microservice REST API Documentation",
                description = "CalixtoBank Cards microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Pedro Calixto",
                        url = "https://www.pgcalixto.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.pgcalixto.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "CalixtoBank Cards microservice REST API Documentation"
        )
)
public class CardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardsApplication.class, args);
    }
}
