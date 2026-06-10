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
public class StateRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String name;

    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL)
    private String stateHeadEmail;
}
