package com.coditas.electricityservicemanagement.tenant.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterUpdateRequest {
    @NotNull(message = NOT_NULL)
    private Long meterTypeId;

    private String meterName;

    private String description;
}
