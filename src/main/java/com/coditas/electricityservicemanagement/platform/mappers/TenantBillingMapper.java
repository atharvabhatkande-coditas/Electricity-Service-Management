package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import com.coditas.electricityservicemanagement.platform.entity.TenantBilling;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantBillingMapper {
    public TenantBillingResponse tenantBillingResponse(TenantBilling tenantBilling){
        return TenantBillingResponse.builder()
                .tenantBillingId(tenantBilling.getId())
                .tenant(TenantResponseForBill.builder()
                        .tenantId(tenantBilling.getTenant().getId())
                        .providerName(tenantBilling.getTenant().getProviderName())
                        .isActive(tenantBilling.getTenant().isActive())
                        .build())

                .amount(tenantBilling.getAmount())
                .dueDate(tenantBilling.getDueDate())
                .billingDate(tenantBilling.getBillingDate())
                .billingStatus(tenantBilling.getBillingStatus())
                .build();
    }

    public AllTenantBillingResponse allTenantBillingResponse(List<TenantBilling> tenantBillingList){
        Tenant tenant=tenantBillingList.getFirst().getTenant();
        List<TenantBillResponse>tenantBillResponses=tenantBillingList.stream().map(this::tenantBillResponse).toList();
        return AllTenantBillingResponse.builder()
                .tenantId(tenant.getId())
                .providerName(tenant.getProviderName())
                .bills(tenantBillResponses)
                .build();
    }
    public TenantBillResponse tenantBillResponse(TenantBilling tenantBilling){
        return TenantBillResponse.builder()
                .tenantBillingId(tenantBilling.getId())
                .amount(tenantBilling.getAmount())
                .dueDate(tenantBilling.getDueDate())
                .billingDate(tenantBilling.getBillingDate())
                .billingStatus(tenantBilling.getBillingStatus())
                .build();
    }

}
