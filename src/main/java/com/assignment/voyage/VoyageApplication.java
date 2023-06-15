package com.assignment.voyage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VoyageApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoyageApplication.class, args);
    }

}
