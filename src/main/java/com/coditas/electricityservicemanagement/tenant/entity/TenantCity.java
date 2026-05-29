package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.platform.entity.District;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenant_city")
public class TenantCity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "city_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;


}
