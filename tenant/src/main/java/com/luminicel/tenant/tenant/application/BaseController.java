package com.luminicel.tenant.tenant.application;

import com.luminicel.tenant.tenant.domain.DataSourceConfig;
import com.luminicel.tenant.tenant.domain.Tenant;
import com.luminicel.tenant.tenant.domain.TenantForm;
import com.luminicel.tenant.tenant.domain.TenantService;
import com.luminicel.tenant.tenant.infrastructure.DataSourceRepository;
import com.luminicel.tenant.tenant.infrastructure.TenantRepository;
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
    private final TenantService tenantService;

    public BaseController(final Flyway flyway, TenantRepository tenantRepository, DataSourceRepository dataSourceRepository, TenantService tenantService) {
        this.flyway = flyway;
        this.tenantRepository = tenantRepository;
        this.dataSourceRepository = dataSourceRepository;
        this.tenantService = tenantService;
    }

    @PostMapping()
    public ResponseEntity<String> addDatasource(@RequestBody final TenantForm form) {
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
                .databasePassword("Ghaexer610")
                .schema(tenant.getDomain())
//                .databaseUrl("jdbc:postgresql://localhost:5432/" + domain + "?useSSL=false")
                 .databaseUrl("jdbc:postgresql://localhost:5432/" + tenant.getDomain())
                .build();
        dataSourceRepository.save(dataSourceConfig);

        tenantService.createDatabase(dataSourceConfig);


        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    @GetMapping
	public List<DataSourceConfig> getAll() {
		return dataSourceRepository.findAll();
	}
}
