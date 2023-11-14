package com.luminicel.tenant.tenantConfig;

import com.luminicel.tenant.tenant.domain.model.Tenant;
import org.springframework.stereotype.Service;

@Service
public class TenantConfigServiceImpl implements TenantConfigService{
    private final TenantConfigRepository repository;
    private static final String DEFAULT_LOGO = "https://static.rfstat.com/renderforest/images/v2/landing-pics/youtube-logo/471.jpg";
    private static final String DEFAULT_THEME_COLOR = "#f2580c";
    private static final String DEFAULT_BANNER= "https://png.pngtree.com/thumb_back/fh260/back_pic/00/02/44/5056179b42b174f.jpg";
    public TenantConfigServiceImpl(final TenantConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createConfig(TenantConfig tenantConfig) {
        repository.save(tenantConfig);
    }

    @Override
    public void createDefaultConfigForTenant(final Tenant tenant) {
        final TenantConfig tenantConfig = TenantConfig.builder()
                .tenantId(tenant.getId())
                .themeColor(DEFAULT_THEME_COLOR)
                .logoUrl(DEFAULT_LOGO)
                .bannerUrl(DEFAULT_BANNER)
                .domain(tenant.getDomain())
                .build();
        repository.save(tenantConfig);
    }

    @Override
    public TenantConfig getConfigByDomain(final String domain) {
        final TenantConfig tenantConfig = repository.findByDomain(domain).orElseThrow(
                ()-> new IllegalArgumentException(
                        String.format("No tenant with domain %s", domain)
                )
        );
        return tenantConfig;
    }
}
