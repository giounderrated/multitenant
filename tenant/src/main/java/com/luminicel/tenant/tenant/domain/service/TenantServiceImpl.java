package com.luminicel.tenant.tenant.domain.service;

import com.luminicel.tenant.tenant.application.TenantRepository;
import com.luminicel.tenant.tenant.domain.model.Tenant;
import com.luminicel.tenant.tenant.domain.model.TenantForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService{

    private final TenantRepository repository;
    private final CreateTenant creator;
    public TenantServiceImpl(TenantRepository repository, CreateTenant creator) {
        this.repository = repository;
        this.creator = creator;
    }

    @Override
    public String createTenant(final TenantForm form) {
        return creator.create(form);
    }

    @Override
    public List<Tenant> getAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsByDomain(String domain) {
        return repository.existsByDomain(domain);
    }


}
