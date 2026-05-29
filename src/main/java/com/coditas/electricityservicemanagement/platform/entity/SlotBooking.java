package com.coditas.electricityservicemanagement.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "slot_booking")
public class SlotBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private PlatformUsers user;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private TimeSlot slot;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

}
