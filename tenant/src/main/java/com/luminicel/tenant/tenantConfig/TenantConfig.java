package com.luminicel.tenant.tenantConfig;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TenantConfig {
     @Id
    @SequenceGenerator(
            name = "tenant_config_id_sequence",
            sequenceName = "tenant_config_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tenant_config_id_sequence"
    )
    private Long id;
    private String logoUrl;
    private String themeColor;
    private String bannerUrl;
    private Long tenantId;
}
