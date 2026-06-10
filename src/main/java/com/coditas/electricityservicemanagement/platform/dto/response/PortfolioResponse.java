package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioResponse {

    private TenantResponse tenantResponse;
    private String salesPersonEmail;
}
