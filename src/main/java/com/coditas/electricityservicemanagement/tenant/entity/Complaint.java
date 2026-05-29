package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.tenant.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cusromer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "cusromer_meter_id")
    private CustomerMeter customerMeter;

    @Column(name = "description")
    private String description;
    @Column(name = "complaint_status")
    private ComplaintStatus complaintStatus;

    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private TenantUsers personnel;

    @Column(name = "technician_id")
    private Long technicianId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "bpo_id")
    private BPO bpo;


}
