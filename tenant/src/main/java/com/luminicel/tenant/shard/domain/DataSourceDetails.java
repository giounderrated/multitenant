package com.luminicel.tenant.shard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "datasource")
public class DataSourceDetails {
    @Id
    @SequenceGenerator(
            name = "datasource_id_sequence",
            sequenceName = "datasource_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "datasource_id_sequence"
    )
    private Long id;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String schema;
    private Long tenantId;
    private String tenantDomain;
}
