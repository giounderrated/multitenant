package com.luminicel.tenant.tenant.domain.service;

import com.luminicel.tenant.tenant.domain.model.Tenant;
import com.luminicel.tenant.tenant.domain.model.TenantForm;

import java.util.List;

public interface TenantService {
    String createTenant(TenantForm tenant);
    List<Tenant> getAll();


}
