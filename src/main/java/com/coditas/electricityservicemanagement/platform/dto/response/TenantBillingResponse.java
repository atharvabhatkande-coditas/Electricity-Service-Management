package com.coditas.electricityservicemanagement.platform.dto.response;

import com.coditas.electricityservicemanagement.platform.enums.BillingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TenantBillingResponse {
    private Long tenantBillingId;
    private TenantResponseForBill tenant;
    private Double amount;
    private LocalDateTime dueDate;
    private LocalDateTime billingDate;
    private BillingStatus billingStatus;
}
