package com.coditas.electricityservicemanagement.tenant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterBillingPlanRequest {
    @NotNull(message = NOT_NULL)
    private Long meterTypeId;
    @NotNull(message = NOT_NULL)
    private Double ratePerUnit;
    @NotNull(message = NOT_NULL)
    private Double fixedCharge;
    @NotNull(message = NOT_NULL)
    private Integer photosRequired;
    @NotNull(message = NOT_NULL)
    private Integer photosInterval;
}
