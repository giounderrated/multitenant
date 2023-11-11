package com.luminicel.tenant.tenant.infrastructure;

import com.luminicel.tenant.tenant.domain.DataSourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSourceConfig,Long> {
}
