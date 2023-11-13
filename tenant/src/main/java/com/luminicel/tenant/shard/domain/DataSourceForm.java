package com.luminicel.tenant.shard.domain;

public record DataSourceForm(
        String databaseUrl,
        String databaseUsername,
        String databasePassword,
        Long tenantId
) {
}
