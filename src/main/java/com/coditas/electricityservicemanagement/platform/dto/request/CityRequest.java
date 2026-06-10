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
public class CityRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String cityName;
    @NotNull(message = NOT_NULL)
    private Long districtId;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL)
    private String cityHeadEmail;

}
