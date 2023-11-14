package com.luminicel.client.multitenancy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
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
@RefreshScope
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    private final TenantIdentifierResolver tenantIdentifierResolver;
    private static final String DATASOURCE_ENDPOINT = "http://localhost:8080/api/v1/datasources";
    private final RestTemplate restTemplate;

    private Map<Object, Object> targetDataSources = new HashMap<>();

    public TenantRoutingDataSource(TenantIdentifierResolver tenantIdentifierResolver, RestTemplate restTemplate) {

        this.tenantIdentifierResolver = tenantIdentifierResolver;
        this.restTemplate = restTemplate;

        final DataSourceDetails[] dataSourceDetails = getDataSourceDetails();

        for (DataSourceDetails dataSourceDetail : dataSourceDetails) {
            DataSource dataSource = createDataSource(dataSourceDetail);
            targetDataSources.put(dataSourceDetail.tenantDomain(), dataSource);
        }
        setTargetDataSources(targetDataSources);
        setDefaultTargetDataSource(createMasterDataSource());

    }

    private DataSourceDetails[] getDataSourceDetails() {
        final HttpHeaders headers = new HttpHeaders();

        final ResponseEntity<DataSourceDetails[]> response =
                restTemplate.exchange(
                        DATASOURCE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers),
                        DataSourceDetails[].class);
        final DataSourceDetails[] dataSourceDetails = response.getBody();
        return dataSourceDetails;
    }

//    public void addDataSource(final DataSourceDetails details) {
//        final DataSource newDataSource = createDataSource(details);
//        targetDataSources.put(details.tenantDomain(), newDataSource);
//        this.setTargetDataSources(targetDataSources);
//        printAllDataSources();
//    }

//    private void printAllDataSources() {
//        for (Map.Entry<Object, Object> entry : targetDataSources.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//    }

    private DataSource createDataSource(final DataSourceDetails config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.databaseUrl());
        hikariConfig.setUsername(config.databaseUsername());
        hikariConfig.setPassword(config.databasePassword());
        hikariConfig.setSchema(config.schema());
        hikariConfig.setMaximumPoolSize(4);
        return new HikariDataSource(hikariConfig);
    }

    private DataSource createMasterDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/mocking");
        hikariConfig.setSchema("master");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("Ghaexer610");
        hikariConfig.setMaximumPoolSize(4);
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
