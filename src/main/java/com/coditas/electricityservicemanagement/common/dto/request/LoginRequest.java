package com.coditas.electricityservicemanagement.common.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_BLANK;
import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String username;
    @NotBlank(message = NOT_BLANK)
    @NotNull(message = NOT_NULL)
    private String password;
}
