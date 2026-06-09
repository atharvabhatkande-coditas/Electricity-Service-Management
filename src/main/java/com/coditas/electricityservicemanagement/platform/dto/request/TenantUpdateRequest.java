package com.coditas.electricityservicemanagement.platform.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantUpdateRequest {
    private String providerName;
    private Boolean isActive;

}
