package com.luminicel.client.multitenancy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfiguration {

    private static final String DATASOURCE_ENDPOINT = "http://localhost:8080/api/v1/management";
    private final RestTemplate restTemplate;
    private final JpaProperties jpaProperties;

    public DataSourceConfiguration(final RestTemplate restTemplate, final JpaProperties jpaProperties) {
        this.restTemplate = restTemplate;
        this.jpaProperties = jpaProperties;
    }

    @Bean
    @Primary
    public Map<String, DataSource> dataSources() {
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<DataSourceConfig[]> response =
                restTemplate.exchange(
                        DATASOURCE_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers),
                        DataSourceConfig[].class);

        log.info(response.getBody().toString());

        DataSourceConfig[] dataSourceConfigs = response.getBody();

        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceConfig dataSourceConfig : dataSourceConfigs) {
            DataSource dataSource = createDataSource(dataSourceConfig);
            System.out.println(dataSourceConfig.tenantId());
            result.put(dataSourceConfig.tenantDomain(), dataSource);
        }

        return result;
    }

    private DataSource createDataSource(final DataSourceConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.databaseUrl());
        hikariConfig.setUsername(config.databaseUsername());
        hikariConfig.setPassword(config.databasePassword());
        hikariConfig.setKeepaliveTime(40000);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setMaxLifetime(45000);
        hikariConfig.setIdleTimeout(35000);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        Map<String, Object> hibernateProps = new LinkedHashMap<>();
        hibernateProps.putAll(this.jpaProperties.getProperties());
        hibernateProps.put("hibernate.hbm2ddl.auto", "none");
        hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        result.setPackagesToScan("com.luminicel.client");
        result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        result.setJpaPropertyMap(hibernateProps);

        return result;
    }

    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
