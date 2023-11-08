package com.luminicel.client.multitenancy;

import com.luminicel.tenant.tenant.DataSourceConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Lazy
public class DataSourceConfiguration {

    private static final String DATASOURCE_ENDPOINT = "http://localhost:8080/api/v1/management";
    private final RestTemplate restTemplate;


    public DataSourceConfiguration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Primary
    @Bean
    public Map<String, DataSource> dataSources() {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<DataSourceConfig[]> response =
                restTemplate.exchange(
                        DATASOURCE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers),
                        DataSourceConfig[].class);

        DataSourceConfig[] dataSourceConfigs = response.getBody();

        Map<String, DataSource> result = new HashMap<>();


        for (DataSourceConfig dataSourceConfig : dataSourceConfigs) {
            DataSource dataSource = createDataSource(dataSourceConfig);
            result.put(dataSourceConfig.getTenantDomain(), dataSource);
        }

        return result;

    }

    private DataSource createDataSource(final DataSourceConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getDatabaseUrl());
        hikariConfig.setUsername(config.getDatabaseUsername());
        hikariConfig.setPassword(config.getDatabasePassword());
        hikariConfig.setKeepaliveTime(40000);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaxLifetime(45000);
        hikariConfig.setIdleTimeout(35000);
        return new HikariDataSource(hikariConfig);
    }

}
