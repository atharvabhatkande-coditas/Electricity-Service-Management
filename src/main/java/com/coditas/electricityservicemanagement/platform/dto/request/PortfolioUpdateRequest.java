package com.coditas.electricityservicemanagement.platform.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.EMAIL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioUpdateRequest {
    private String tenantId;
    @Email(message = EMAIL)
    private String salesPersonEmail;

    private Boolean isActive;
}
