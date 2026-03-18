package com.tt1.trabajo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan; // añadir este import
import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tt1.trabajo", "servicios", "interfaces"}) // <--- añadir esta línea
public class TrabajoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrabajoApplication.class, args);
    }

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }
}