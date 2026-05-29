package com.coditas.electricityservicemanagement.platform.entity;

import com.coditas.electricityservicemanagement.platform.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenant_billing")
public class TenantBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_status")
    private BillingStatus billingStatus;
}
