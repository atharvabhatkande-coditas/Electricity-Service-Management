package com.coditas.electricityservicemanagement.platform.dto.request;

import com.coditas.electricityservicemanagement.platform.enums.BillingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_NULL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantBillingStatusUpdateRequest {
    @NotNull(message = NOT_NULL)
    private BillingStatus billingStatus;
}
