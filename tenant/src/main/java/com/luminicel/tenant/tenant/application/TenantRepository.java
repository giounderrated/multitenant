package com.luminicel.tenant.tenant.application;

import com.luminicel.tenant.tenant.domain.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant,Long> {
    boolean existsByDomain(String domain);
}
