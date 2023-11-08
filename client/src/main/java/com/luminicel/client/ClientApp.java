package com.luminicel.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.luminicel.tenant","com.luminicel.client"})
@ComponentScan(basePackages = {"com.luminicel.tenant","com.luminicel.client"})
@EnableJpaRepositories(basePackages = {"com.luminicel.tenant"})
public class ClientApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ClientApp.class);
    }
}
