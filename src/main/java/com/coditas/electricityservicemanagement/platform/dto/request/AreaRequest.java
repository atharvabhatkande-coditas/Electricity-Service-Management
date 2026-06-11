package com.coditas.electricityservicemanagement.platform.dto.request;

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
public class AreaRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String areaName;
    @NotNull(message = NOT_NULL)
    private Long cityId;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String pincode;
}
