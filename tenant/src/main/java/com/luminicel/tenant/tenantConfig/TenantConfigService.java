package com.luminicel.tenant.tenantConfig;

public interface TenantConfigService {
    void createConfig(TenantConfig tenantConfig);
    void createDefaultConfigForTenantWithId(Long id);

    TenantConfig getConfigWithTenantId(Long id);
}
