package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.StateResponse;
import com.coditas.electricityservicemanagement.platform.entity.State;
import org.springframework.stereotype.Component;

@Component
public class StateMapper {

    public StateResponse toDto(State state){
        return StateResponse.builder()
                .stateId(state.getId())
                .stateName(state.getName())
                .stateHeadEmail(state.getStateHead().getUsername())
                .build();
    }
}
