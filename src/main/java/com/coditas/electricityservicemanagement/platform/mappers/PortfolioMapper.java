package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.PortfolioResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.TenantResponse;
import com.coditas.electricityservicemanagement.platform.entity.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {

    public PortfolioResponse portfolioResponse(Portfolio portfolio){
        return PortfolioResponse.builder()
                .tenantResponse(TenantResponse.builder()
                        .tenantId(portfolio.getTenant().getId())
                        .providerName(portfolio.getTenant().getProviderName())
                        .schemaName(portfolio.getTenant().getSchemaName())
                        .isActive(portfolio.getTenant().isActive())
                        .invitedBy(portfolio.getTenant().getOnboardedBy().getUsername())
                        .build())
                .salesPersonEmail(portfolio.getSalesUserId().getUsername())
                .build();
    }
}
