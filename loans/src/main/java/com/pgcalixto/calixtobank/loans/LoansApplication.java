package com.pgcalixto.calixtobank.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.pgcalixto.calixtobank.loans.controller") })
@EnableJpaRepositories("com.pgcalixto.calixtobank.loans.repository")
@EntityScan("com.pgcalixto.calixtobank.loans.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
        info = @Info(
                title = "Loans microservice REST API Documentation",
                description = "CalixtoBank Loans microservice REST API Documentation",
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
                description = "CalixtoBank Loans microservice REST API Documentation"
        )
)
public class LoansApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }
}
