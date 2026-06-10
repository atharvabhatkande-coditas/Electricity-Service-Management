package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantResponseForBill {
    private String tenantId;
    private String providerName;
    private Boolean isActive;
}
