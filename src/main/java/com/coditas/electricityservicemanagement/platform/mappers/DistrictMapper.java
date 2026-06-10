package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.DistrictResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.StateResponse;
import com.coditas.electricityservicemanagement.platform.entity.District;
import org.springframework.stereotype.Component;

@Component
public class DistrictMapper {

    public DistrictResponse toDto(District district){
        return DistrictResponse.builder()
                .districtId(district.getId())
                .districtName(district.getName())
                .state(StateResponse.builder()
                        .stateId(district.getState().getId())
                        .stateName(district.getState().getName())
                        .stateHeadEmail(district.getState().getStateHead().getUsername())
                        .build())
                .districtHeadEmail(district.getDistrictHead().getUsername())
                .build();
    }
}
