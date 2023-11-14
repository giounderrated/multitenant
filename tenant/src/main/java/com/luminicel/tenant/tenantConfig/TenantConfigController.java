package com.luminicel.tenant.tenantConfig;

import com.luminicel.tenant.rest.JSend;
import com.luminicel.tenant.rest.Success;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
public class TenantConfigController {
    public static final String FOR_TENANT = "/{tenantId}";

    private final TenantConfigService configService;

    public TenantConfigController(TenantConfigService configService) {
        this.configService = configService;
    }

    @GetMapping(FOR_TENANT)
    public JSend<TenantConfig> getConfig(@PathVariable final String tenantId){
        final TenantConfig tenantConfig=  configService.getConfigWithTenantId(Long.valueOf(tenantId));
        return Success.<TenantConfig>builder()
                .data(tenantConfig)
                .build();
    }
}
