package com.coditas.electricityservicemanagement.platform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.EMAIL;
import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_BLANK;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantOnBoardRequest {
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = "NOT_NULL")
    private String providerName;
    @Email(message = EMAIL)
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = "NOT_NULL")
    private String tenantPocEmail;
}
