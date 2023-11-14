package com.luminicel.tenant.tenantConfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantConfigRepository extends JpaRepository<TenantConfig,Long> {
    Optional<TenantConfig> findByTenantId(Long id);
}
