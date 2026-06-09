package com.coditas.electricityservicemanagement.platform.entity;

import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "tenant_id")
    private String tenantId;
    @Column(name = "code")
    private String code;
    @Column(name = "expire_at")
    private Instant expireAt;

    @ManyToOne
    @JoinColumn(name = "invited_by")
    private PlatformUsers invitedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleType role;

}
