package com.coditas.electricityservicemanagement.platform.entity;

import com.coditas.electricityservicemanagement.platform.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "available_slots")
public class AvailableSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PlatformUsers userId;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private TimeSlot slot;

    @Column(name = "available_date")
    private LocalDateTime availableDate;

    @Column(name = "availability_status")
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;



}
