package com.coditas.electricityservicemanagement.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_BLANK;
import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateBillRequest {
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private String tenantId;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private Double amount;
    @NotNull(message = NOT_NULL)
    @NotBlank(message = NOT_BLANK)
    private LocalDateTime dueDate;
}
