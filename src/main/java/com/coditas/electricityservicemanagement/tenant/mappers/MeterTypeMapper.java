package com.coditas.electricityservicemanagement.tenant.mappers;

import com.coditas.electricityservicemanagement.tenant.dto.response.MeterTypeResponse;
import com.coditas.electricityservicemanagement.tenant.entity.MeterType;
import org.springframework.stereotype.Component;

@Component
public class MeterTypeMapper {

    public MeterTypeResponse meterTypeResponse(MeterType meterType){

        return MeterTypeResponse.builder()
                .meterTypeName(meterType.getName())
                .description(meterType.getDescription())
                .build();
    }
}
