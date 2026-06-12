package com.coditas.electricityservicemanagement.tenant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_BLANK;
import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterTypeRequest {
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String meterName;
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String description;
}
