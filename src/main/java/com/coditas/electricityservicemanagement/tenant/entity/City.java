package com.coditas.electricityservicemanagement.tenant.entity;

import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "city_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn(name = "city_head_id")
    private PlatformUsers cityHead;
}
