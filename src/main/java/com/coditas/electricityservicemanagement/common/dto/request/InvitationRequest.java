package com.coditas.electricityservicemanagement.common.dto.request;

import com.coditas.electricityservicemanagement.tenant.enums.RoleType;
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
public class InvitationRequest {
    @Email(message = EMAIL)
    private String email;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String tenantId;
    @NotNull(message = NOT_NULL)
    private RoleType role;
}
