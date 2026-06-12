package com.coditas.electricityservicemanagement.tenant.mappers;

import com.coditas.electricityservicemanagement.tenant.dto.response.MeterBillingPlanResponse;
import com.coditas.electricityservicemanagement.tenant.entity.MeterBillingPlan;
import org.springframework.stereotype.Component;

@Component
public class MeterBillingPlanMapper {

    public MeterBillingPlanResponse  meterBillingPlanResponse(MeterBillingPlan meterBillingPlan){
        return MeterBillingPlanResponse.builder()
                .meterType(meterBillingPlan.getMeterType().getName())
                .description(meterBillingPlan.getMeterType().getDescription())
                .ratePerUnit(meterBillingPlan.getRatePerUnit())
                .fixedCharge(meterBillingPlan.getFixedCharge())
                .build();
    }
}
