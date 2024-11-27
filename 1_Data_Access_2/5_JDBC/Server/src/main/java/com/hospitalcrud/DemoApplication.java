package com.hospitalcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DemoApplication.class);
        application.setAdditionalProfiles("JDBC");
        SpringApplication.run(DemoApplication.class, args);
    }
}