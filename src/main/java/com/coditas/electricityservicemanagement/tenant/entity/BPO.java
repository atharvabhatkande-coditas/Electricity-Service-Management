package com.coditas.electricityservicemanagement.tenant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bpo")
public class BPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "bpo_name")
    private String name;

    @Column(name = "is_active")
    private boolean isActive;

}
