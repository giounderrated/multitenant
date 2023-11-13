package com.luminicel.tenant.shard.infrastructure;

import com.luminicel.tenant.shard.domain.DataSourceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceDetailsJPARepository extends JpaRepository<DataSourceDetails,Long> {
}
