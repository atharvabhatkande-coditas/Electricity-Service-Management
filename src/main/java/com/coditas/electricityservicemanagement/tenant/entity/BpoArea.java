package com.coditas.electricityservicemanagement.tenant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bpo_area")
public class BpoArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bpo_id")
    private BPO bpo;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;
}
