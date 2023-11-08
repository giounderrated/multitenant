package com.luminicel.tenant;

import com.luminicel.tenant.tenant.*;
import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
public class BaseController {

    private final Flyway flyway;

    private final TenantRepository tenantRepository;
    private final DataSourceRepository dataSourceRepository;

    public BaseController(final Flyway flyway, TenantRepository tenantRepository, DataSourceRepository dataSourceRepository) {
        this.flyway = flyway;
        this.tenantRepository = tenantRepository;
        this.dataSourceRepository = dataSourceRepository;
    }

    @PostMapping("/{domain}")
    public ResponseEntity<String> addDatasource(@RequestBody final TenantForm form, @PathVariable final String domain) {
        // TODO Get the tenant info from repository?

        final Tenant tenant = Tenant.builder()
                .domain(form.domain())
                .email(form.email())
                .firstname(form.firstname())
                .build();
        tenantRepository.saveAndFlush(tenant);

        final DataSourceConfig dataSourceConfig = DataSourceConfig.builder()
                .tenantId(tenant.getId())
                .tenantDomain(tenant.getDomain())
                .databaseUsername("postgres")
                .databasePassword("most-secure-password")
//                .databaseUrl("jdbc:postgresql://localhost:5432/" + domain + "?useSSL=false")
                 .databaseUrl("jdbc:postgresql://localhost:5432/" + domain)
                .build();
        dataSourceRepository.save(dataSourceConfig);
        Flyway fly = Flyway.configure()
                .configuration(flyway.getConfiguration())
//                .dataSource(dataSource.getDatabaseUrl(),dataSource.getDatabaseUsername(),dataSource.getDatabasePassword())
                .schemas(tenant.getDomain())
                .defaultSchema(tenant.getDomain())
                .load();

        fly.migrate();

        return new ResponseEntity<>("success", HttpStatus.OK);


    }

    @GetMapping
	public List<DataSourceConfig> getAll() {
		return dataSourceRepository.findAll();
	}
}
