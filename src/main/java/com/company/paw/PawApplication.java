package com.company.paw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages="com.company.paw")
@EnableMongoAuditing
public class PawApplication {
    public static void main(String[] args) {
        SpringApplication.run(PawApplication.class, args);
    }
}
