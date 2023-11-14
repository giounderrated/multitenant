package com.luminicel.tenant.tenant.infrastructure;

import com.luminicel.tenant.rest.JSend;
import com.luminicel.tenant.rest.Success;
import com.luminicel.tenant.tenant.domain.model.Tenant;
import com.luminicel.tenant.tenant.domain.model.TenantForm;
import com.luminicel.tenant.tenant.domain.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantController {
    private static final String TENANT_WITH_ID = "/{id}";
    private static final String REGISTER_PATH = "/register";
    private static final String TENANT_CHECK = "/check/{domain}";

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping(REGISTER_PATH)
    public JSend<String> register(@RequestBody final TenantForm form) {
        final String result = tenantService.createTenant(form);
        return Success.<String>builder()
                .data(result)
                .build();
    }

    @GetMapping
    public List<Tenant> getAllTenants(){
        return tenantService.getAll();
    }

    @GetMapping(TENANT_CHECK)
    public ResponseEntity<Boolean> checkByDomain(@PathVariable("domain") final String domain){
        final boolean exists = tenantService.existsByDomain(domain);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
//        return Success.<Boolean>builder()
//                .data(exists)
//                .build();
    }


}
