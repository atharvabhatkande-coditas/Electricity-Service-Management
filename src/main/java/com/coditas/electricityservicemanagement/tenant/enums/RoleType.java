package com.coditas.electricityservicemanagement.tenant.enums;

import lombok.Getter;

import java.util.Comparator;
import java.util.Set;
@Getter
public enum RoleType {
    POC(1),
    OPERATIONAL_HEAD(2),
    HIGHER_MANAGER(3),
    MANAGER(4),
    PERSONNEL(5);

    private final int value;
    RoleType(final int priority){
        value=priority;
    }

    public static RoleType getHighestPriorityRoleTenant(Set<RoleType> roles){
        return roles
                .stream()
                .min(Comparator.comparingInt(RoleType::getValue))
                .orElse(null);
    }
}
