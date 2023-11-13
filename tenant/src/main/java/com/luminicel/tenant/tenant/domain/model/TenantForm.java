package com.luminicel.tenant.tenant.domain.model;

public record TenantForm(
        String firstname,
        String lastname,
        String password,
        String email,
        String domain
) {
}
