package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PlatformUsers user;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private TenantState tenantState;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private TenantDistrict tenantDistrict;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private TenantCity tenantCity;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private TenantArea tenantArea;

    @Column(name = "pincode")
    private String pincode;


}
