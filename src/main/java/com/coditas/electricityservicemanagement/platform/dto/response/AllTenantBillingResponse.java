package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllTenantBillingResponse {

   private String tenantId;
   private String providerName;
   private List<TenantBillResponse> bills;
}
