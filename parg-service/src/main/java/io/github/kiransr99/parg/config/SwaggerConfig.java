package io.github.kiransr99.parg.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Physical Assessment ReportGenerator REST API")
                        .description("Some custom description of API.")
                        .version("1.0")
                        .contact(new Contact().name("PARG")
                                .email("").url(""))
                        .license(new License()
                                .name("License of API")
                                .url("API license URL")));
    }
}
