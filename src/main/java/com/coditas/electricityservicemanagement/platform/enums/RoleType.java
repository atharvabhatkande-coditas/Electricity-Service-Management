package com.coditas.electricityservicemanagement.platform.enums;

import lombok.Getter;

import java.util.Comparator;
import java.util.Set;

@Getter
public enum RoleType {

    SUPER_ADMIN(1),
    MANAGEMENT(2),
    SALES(3),
    STATE_HEAD(4),
    DISTRICT_HEAD(5),
    CITY_HEAD(6),
    CRM_AGENT(7),
    TECHNICIAN(8),
    BILLER(9),
    CUSTOMER(10),
    POC(11);

    private final int value;
    RoleType(final int priority){
        value=priority;
    }

    public static RoleType getHighestPriorityRole(Set<RoleType>roles){
        return roles
                .stream()
                .min(Comparator.comparingInt(RoleType::getValue))
                .orElse(null);
    }

}
