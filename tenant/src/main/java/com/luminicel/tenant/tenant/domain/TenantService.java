package com.luminicel.tenant.tenant.domain;

import com.luminicel.tenant.tenant.domain.DataSourceConfig;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

@Service
public class TenantService {
    private final Flyway flyway;

    public TenantService(final Flyway flyway) {
        this.flyway = flyway;
    }

    public void createDatabase(final DataSourceConfig config) {
        Flyway fly = Flyway.configure()
                .configuration(flyway.getConfiguration())
                .locations("db/migrations")
                .schemas(config.getTenantDomain())
                .defaultSchema(config.getTenantDomain())
                .load();
        fly.migrate();
    }
}
