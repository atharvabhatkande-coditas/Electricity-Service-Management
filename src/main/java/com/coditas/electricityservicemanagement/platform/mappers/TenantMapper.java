package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.TenantResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public TenantResponse getALlToDto(Tenant tenant, PlatformUsers platformUser){
        return TenantResponse.builder()
                .tenantId(tenant.getId())
                .providerName(tenant.getProviderName())
                .schemaName(tenant.getSchemaName())
                .isActive(tenant.isActive())
                .invitedBy(platformUser.getUsername())
                .build();
    }
}
