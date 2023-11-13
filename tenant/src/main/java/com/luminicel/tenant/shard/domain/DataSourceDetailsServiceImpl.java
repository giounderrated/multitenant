package com.luminicel.tenant.shard.domain;

import com.luminicel.tenant.shard.infrastructure.DataSourceDetailsJPARepository;
import com.luminicel.tenant.tenant.application.TenantRepository;
import com.luminicel.tenant.tenant.domain.exception.TenantNotFoundException;
import com.luminicel.tenant.tenant.domain.model.Tenant;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class DataSourceDetailsServiceImpl implements DataSourceDetailsService{
    private final Flyway flyway;
    private final DataSourceDetailsJPARepository repository;
    private final TenantRepository tenantRepository;

    public static final String MIGRATIONS_PATH = "db/migrations";

    public DataSourceDetailsServiceImpl(Flyway flyway, DataSourceDetailsJPARepository repository, TenantRepository tenantRepository) {
        this.flyway = flyway;
        this.repository = repository;
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public void createDataSource(final DataSourceForm form) {

        final Tenant tenant = tenantRepository.findById(form.tenantId()).orElseThrow(
                ()-> new TenantNotFoundException(String.format("Tenant with id %s does not exist",form.tenantId()))
        );

//        final DataSourceDetails details = DataSourceDetails.builder()
//                .databaseUrl(form.databaseUrl())
//                .databasePassword(form.databasePassword())
//                .databaseUsername(form.databaseUsername())
//                .tenantId(tenant.getId())
//                .tenantDomain(tenant.getDomain())
//                .build();

        final DataSourceDetails details = DataSourceDetails.builder()
                .tenantId(tenant.getId())
                .tenantDomain(tenant.getDomain())
                .databaseUsername("postgres")
                .databasePassword("Ghaexer610")
                .schema(tenant.getDomain())
//                .databaseUrl("jdbc:postgresql://localhost:5432/" + domain + "?useSSL=false")
                 .databaseUrl("jdbc:postgresql://localhost:5432/" + tenant.getDomain())
                .build();

        repository.save(details);

        Flyway fly = Flyway.configure()
                .configuration(flyway.getConfiguration())
                .locations(MIGRATIONS_PATH)
                .dataSource(
                        details.getDatabaseUrl(),
                        details.getDatabaseUsername(),
                        details.getDatabasePassword()
                )
                .schemas(details.getSchema())
                .defaultSchema(details.getSchema())
                .load();
        fly.migrate();
    }

    @Override
    public List<DataSourceDetails> getAll() {
        return repository.findAll();
    }
}
