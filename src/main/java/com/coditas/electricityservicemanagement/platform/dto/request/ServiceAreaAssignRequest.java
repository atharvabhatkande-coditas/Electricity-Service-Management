package com.coditas.electricityservicemanagement.platform.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceAreaAssignRequest {
    @NotNull(message = NOT_NULL)
    private Long serviceAreaId;
    @Email(message = EMAIL)
    private String technicianEmail;
    @Email(message = EMAIL)
    private String billerEmail;
}
