package com.coditas.electricityservicemanagement.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "sales_user_id")
    private PlatformUsers salesUserId;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private PlatformUsers assignedBy;

    @Column(name = "is_active")
    private boolean isActive;
}
