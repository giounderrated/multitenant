package com.luminicel.client.multitenancy;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class DataSourceBasedMultitenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements HibernatePropertiesCustomizer {

    private final Map<String, DataSource> dataSourceMap;

    public DataSourceBasedMultitenantConnectionProvider(Map<String, DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }


    @Override
    protected DataSource selectAnyDataSource() {
        return dataSourceMap.get("master");
    }

    @Override
    protected DataSource selectDataSource(String domain) {
        return dataSourceMap.get(domain);
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,this);
    }
}
