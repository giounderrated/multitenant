package com.luminicel.client.multitenancy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.ShardingKeyBuilder;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    private final TenantIdentifierResolver tenantIdentifierResolver;
    private static final String DATASOURCE_ENDPOINT = "http://localhost:8080/api/v1/management";
    private final RestTemplate restTemplate;

    public TenantRoutingDataSource(TenantIdentifierResolver tenantIdentifierResolver, RestTemplate restTemplate) {
        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.restTemplate = restTemplate;
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<DataSourceDetails[]> response =
                restTemplate.exchange(
                        DATASOURCE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers),
                        DataSourceDetails[].class);

        DataSourceDetails[] dataSourceDetails = response.getBody();

        Map<Object,Object> targetDataSources = new HashMap<>();
        System.out.println("Getting Datasources");
        for (DataSourceDetails dataSourceDetails : dataSourceDetails) {
            DataSource dataSource = createDataSource(dataSourceDetails);
            log.info(dataSourceDetails.toString());
            targetDataSources.put(dataSourceDetails.tenantDomain(), dataSource);
        }
        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(createMasterDataSource());

    }

     private DataSource createDataSource(final DataSourceDetails config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.databaseUrl());
        hikariConfig.setUsername(config.databaseUsername());
        hikariConfig.setPassword(config.databasePassword());
        hikariConfig.setSchema(config.tenantDomain());
        hikariConfig.setKeepaliveTime(40000);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaxLifetime(45000);
        hikariConfig.setIdleTimeout(35000);
        return new HikariDataSource(hikariConfig);
    }

     private DataSource createMasterDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/mocking");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("Ghaexer610");
        hikariConfig.setKeepaliveTime(40000);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaxLifetime(45000);
        hikariConfig.setIdleTimeout(35000);
        return new HikariDataSource(hikariConfig);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantIdentifierResolver.resolveCurrentTenantIdentifier();
    }


    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return super.createConnectionBuilder();
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return super.createShardingKeyBuilder();
    }
}
