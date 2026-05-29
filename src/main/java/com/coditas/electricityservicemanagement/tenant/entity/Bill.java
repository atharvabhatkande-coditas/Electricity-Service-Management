package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.platform.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cusromer_meter_id")
    private CustomerMeter customerMeter;

    @ManyToOne
    @JoinColumn(name = "meter_reading_id")
    private MeterReading meterReading;

    @Column(name = "tax")
    private double tax;
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "bill_status")
    private BillingStatus billStatus;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;


}
