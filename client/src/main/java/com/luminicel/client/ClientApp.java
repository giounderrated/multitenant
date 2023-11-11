package com.luminicel.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.luminicel")
public class ClientApp {
    public static void main(String[] args) {
        SpringApplication.run(ClientApp.class);
    }
}
