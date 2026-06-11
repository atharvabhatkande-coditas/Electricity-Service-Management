package com.coditas.electricityservicemanagement.platform.dto.request;

import com.coditas.electricityservicemanagement.platform.enums.ServiceType;
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
public class ServiceAreaRequest {
    @NotNull(message = NOT_NULL)
    private Long areaId;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL)
    private String employeeEmail;
    @NotNull(message = NOT_NULL)
    private ServiceType serviceType;

}
