package com.coditas.electricityservicemanagement.tenant.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantStateRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String stateName;
}
