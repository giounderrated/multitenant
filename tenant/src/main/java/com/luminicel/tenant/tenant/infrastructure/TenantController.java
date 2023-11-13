package com.luminicel.tenant.tenant.infrastructure;

import com.luminicel.tenant.rest.JSend;
import com.luminicel.tenant.rest.Success;
import com.luminicel.tenant.tenant.domain.model.Tenant;
import com.luminicel.tenant.tenant.domain.model.TenantForm;
import com.luminicel.tenant.tenant.domain.service.TenantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TenantController {
    private static final String TENANT_PATH = "/tenants";
    private static final String TENANT_WITH_ID = "/tenants/{id}";
    private static final String REGISTER_PATH = "/tenants/register";

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

    @GetMapping(TENANT_PATH)
    public List<Tenant> getAllTenants(){
        return tenantService.getAll();
    }


}
