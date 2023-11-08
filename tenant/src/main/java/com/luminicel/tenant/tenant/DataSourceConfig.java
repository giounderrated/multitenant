package com.luminicel.tenant.tenant;

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
public class DataSourceConfig {
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
    private Long tenantId;
    private String tenantDomain;
}
