package com.luminicel.tenant.shard.domain;

import java.util.List;

public interface DataSourceDetailsService {
    void createDataSource(DataSourceForm dataSourceForm);
    List<DataSourceDetails> getAll();
}
