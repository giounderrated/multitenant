package com.luminicel.client.multitenancy;

public record DataSourceConfig(
        Long id,
        String databaseUrl,
        String databaseUsername,
        String databasePassword,
        Long tenantId,
        String tenantDomain
) {
}
