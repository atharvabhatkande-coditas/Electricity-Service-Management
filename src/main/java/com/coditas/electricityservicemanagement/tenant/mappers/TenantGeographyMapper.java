package com.coditas.electricityservicemanagement.tenant.mappers;

import com.coditas.electricityservicemanagement.tenant.dto.response.TenantStateResponse;
import com.coditas.electricityservicemanagement.tenant.entity.TenantState;
import org.springframework.stereotype.Component;

@Component
public class TenantGeographyMapper {

    public TenantStateResponse tenantStateResponse(TenantState tenantState){
        return TenantStateResponse.builder().stateName(tenantState.getName()).build();
    }
}
