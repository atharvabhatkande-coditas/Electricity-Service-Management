package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantResponse {
    private String tenantId;
    private String providerName;
    private String schemaName;
    private boolean isActive;
    private String invitedBy;
}
