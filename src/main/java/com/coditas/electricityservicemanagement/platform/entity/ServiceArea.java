package com.coditas.electricityservicemanagement.platform.entity;

import com.coditas.electricityservicemanagement.platform.enums.ServiceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "service_area")
public class ServiceArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PlatformUsers user;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;
}
