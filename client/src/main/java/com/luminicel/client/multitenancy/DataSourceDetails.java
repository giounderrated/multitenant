package com.luminicel.client.multitenancy;

public record DataSourceDetails(
        Long id,
        String databaseUrl,
        String databaseUsername,
        String databasePassword,
        String schema,
        Long tenantId,
        String tenantDomain
) {
}
