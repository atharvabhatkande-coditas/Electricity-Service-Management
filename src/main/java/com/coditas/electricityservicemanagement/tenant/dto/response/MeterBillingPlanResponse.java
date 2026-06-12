package com.coditas.electricityservicemanagement.tenant.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterBillingPlanResponse {

    private String meterType;
    private String description;

    private Double ratePerUnit;
    private Double fixedCharge;
}
