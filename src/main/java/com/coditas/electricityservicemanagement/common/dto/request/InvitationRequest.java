package com.coditas.electricityservicemanagement.common.dto.request;

import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import jakarta.validation.constraints.Email;

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
    private String tenantId;
    @NotNull(message = NOT_NULL)
    private RoleType role;

}
