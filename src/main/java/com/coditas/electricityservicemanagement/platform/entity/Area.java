package com.coditas.electricityservicemanagement.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "area_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "pincode")
    private Integer pincode;
}
