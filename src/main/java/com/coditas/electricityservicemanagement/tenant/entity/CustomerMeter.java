package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.tenant.enums.CustomerMeterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "customer_meter")
public class CustomerMeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "connection_id")
    private CustomerMeterConnection connection;

    @Column(name = "meter_no")
    private String meterNo;

    @Column(name = "installation_date")
    private LocalDateTime installationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "meter_status")
    private CustomerMeterStatus customerMeterStatus;
}
