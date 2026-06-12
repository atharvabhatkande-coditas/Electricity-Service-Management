package com.coditas.electricityservicemanagement.tenant.dto.request;

import com.coditas.electricityservicemanagement.tenant.enums.RoleType;
import jakarta.validation.constraints.Email;
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
public class TenantInvitationRequest {
    @Email
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String email;
    @NotNull(message = NOT_NULL)
    private RoleType role;
}
