package org.tenpo.challenge.cvergara;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Documentacion REST API Tenpo",
                description = "Tenpo Challenge para Backend ",
                version = "v1",
                contact = @Contact(
                        name = "Cristopher Vergara Colombo",
                        email = "cristopher.vergara.colombo@gmail.com",
                        url = "https://www.linkedin.com/in/cristophervergaracolombo/"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://es.wikipedia.org/wiki/Apache_License"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description =  "Documentacion externa",
                url = "https://docs.spring.io/spring-framework/reference/index.html"
        )
)
public class CvergaraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CvergaraApplication.class, args);
    }

}
