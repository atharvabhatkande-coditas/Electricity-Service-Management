package com.coditas.electricityservicemanagement.tenant.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterBillingPlanUpdateRequest {
    @NotNull(message = NOT_NULL)
    private Long meterTypeId;
    private Double ratePerUnit;
    private Double fixedCharge;
    private Integer photosRequired;
    private Integer photosInterval;
}
