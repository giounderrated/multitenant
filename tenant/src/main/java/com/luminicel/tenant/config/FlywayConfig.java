package com.luminicel.tenant.config;


import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    private final DataSource dataSource;

    public FlywayConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // do nothing
        };
    }

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .locations("db/migrations/default")
                .dataSource(dataSource)
                .schemas("MASTER")
                .load();
        return flyway;
    }


}
