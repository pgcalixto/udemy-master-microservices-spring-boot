package com.pgcalixto.calixtobank.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// @ComponentScans({ @ComponentScan("com.pgcalixto.calixtobank.accounts.controller") })
// @EnableJpaRepositories("com.pgcalixto.calixtobank.accounts.repository")
// @EntityScan("com.pgcalixto.calixtobank.accounts.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "CalixtoBank Accounts microservice REST API Documentation",
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
                description = "CalixtoBank Accounts microservice REST API Documentation"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
