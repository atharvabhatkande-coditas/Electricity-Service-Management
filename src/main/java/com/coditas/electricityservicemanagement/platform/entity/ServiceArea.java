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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private PlatformUsers technician;

    @ManyToOne
    @JoinColumn(name = "biller_id")
    private PlatformUsers biller;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;


}
