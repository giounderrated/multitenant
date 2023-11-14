package com.luminicel.tenant.tenantConfig;

import com.luminicel.tenant.tenant.domain.model.Tenant;

public interface TenantConfigService {
    void createConfig(TenantConfig tenantConfig);
    void createDefaultConfigForTenant(final Tenant tenant);
    TenantConfig getConfigByDomain(final String domain);
}
