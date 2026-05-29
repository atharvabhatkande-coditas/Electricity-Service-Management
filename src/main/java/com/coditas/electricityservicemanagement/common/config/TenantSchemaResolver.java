package com.coditas.electricityservicemanagement.common.config;


import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import com.coditas.electricityservicemanagement.platform.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TenantSchemaResolver {
    private final TenantRepository tenantRepository;
    private static final String PUBLIC_SCHEMA="public";

    public String getTenantSchema(String tenantId){
        if(tenantId==null){
            return PUBLIC_SCHEMA;
        }

        Tenant tenant=tenantRepository.findById(tenantId).orElse(null);
        if(Objects.isNull(tenant)){
            return PUBLIC_SCHEMA;
        }
        else{
            return ("tenant_"+tenant.getProviderName());
        }

    }
}
