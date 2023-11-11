package com.luminicel.tenant.tenant.domain;

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
public class Tenant{
    @Id
    @SequenceGenerator(
            name = "tenant_id_sequence",
            sequenceName = "tenant_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tenant_id_sequence"
    )
    private Long id;
    private String domain;
    private String email;
    private String firstname;

}
