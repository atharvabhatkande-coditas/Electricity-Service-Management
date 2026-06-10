package com.coditas.electricityservicemanagement.tenant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "meter_billing_plan")
public class MeterBillingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meter_type_id")
    private MeterType meter;

    @Column(name = "rate_per_unit")
    private double ratePerUnit;

    @Column(name = "fixed_charge")
    private double fixedCharge;

    @Column(name = "photos_required")
    private int photosRequired;

    @Column(name = "photos_interval")
    private int photos_interval;


}
