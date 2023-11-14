package com.luminicel.tenant.shard.domain;

import com.luminicel.tenant.shard.infrastructure.DataSourceDetailsJPARepository;
import com.luminicel.tenant.tenant.application.TenantRepository;
import com.luminicel.tenant.tenant.domain.exception.TenantNotFoundException;
import com.luminicel.tenant.tenant.domain.model.Tenant;
import org.flywaydb.core.Flyway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataSourceDetailsServiceImpl implements DataSourceDetailsService {
    private final Flyway flyway;
    private final DataSourceDetailsJPARepository repository;
    private final TenantRepository tenantRepository;
    private final RestTemplate template;

    public static final String MIGRATIONS_PATH = "db/migrations";

    public DataSourceDetailsServiceImpl(Flyway flyway, DataSourceDetailsJPARepository repository, TenantRepository tenantRepository, RestTemplate template) {
        this.flyway = flyway;
        this.repository = repository;
        this.tenantRepository = tenantRepository;
        this.template = template;
    }

    @Override
    @Transactional
    public void createDataSource(final DataSourceForm form) {
        final Tenant tenant = tenantRepository.findById(form.tenantId()).orElseThrow(
                () -> new TenantNotFoundException(String.format("Tenant with id %s does not exist", form.tenantId()))
        );
        persistAndMigrateDataSource(form,tenant);
    }

    private void persistAndMigrateDataSource(final DataSourceForm form, final Tenant tenant) {
//      TODO In production, this should be done
//
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

        migrate(details);
        refreshApplicationDataSourcesAtRuntime(details);

    }

    private void refreshApplicationDataSourcesAtRuntime(final DataSourceDetails details) {
        final String endpoint = "http://localhost:8081/actuator/refresh";
        template.postForEntity(endpoint, details, Void.class);
    }

    private void migrate(final DataSourceDetails details) {
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
