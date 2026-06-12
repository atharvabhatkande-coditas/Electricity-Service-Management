package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.tenant.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenant_invitation")
public class TenantInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "invited_by")
    private TenantUsers invitedBy;

    @Column(name = "expire_at")
    private Instant expireAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleType role;

}
