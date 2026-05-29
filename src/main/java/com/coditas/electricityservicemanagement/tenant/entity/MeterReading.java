package com.coditas.electricityservicemanagement.tenant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "meter_reading")
public class MeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_meter_id")
    private CustomerMeter customerMeter;

    @Column(name = "captured_by")
    private Long capturedBy;

    @Column(name = "units_consumed")
    private long unitsConsumed;

    @Column(name = "reading_date")
    private LocalDateTime reading_date;
}
