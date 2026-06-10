package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.platform.entity.State;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenant_district")
public class TenantDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

}
