package com.coditas.electricityservicemanagement.platform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioAssignRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String tenantId;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL)
    private String salesPersonEmail;
}
