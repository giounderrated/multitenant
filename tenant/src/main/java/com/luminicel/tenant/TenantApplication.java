package com.luminicel.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.luminicel.tenant","com.luminicel.mailing"})
public class TenantApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(TenantApplication.class);
    }

}
