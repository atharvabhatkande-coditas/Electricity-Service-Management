package com.coditas.electricityservicemanagement.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "provider_name")
    private String providerName;
    @Column(name = "schema_name")
    private String schemaName;
    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "onboarded_by")
    private MasterUsers onboardedBy;

}
