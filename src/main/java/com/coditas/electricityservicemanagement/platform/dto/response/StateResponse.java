package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateResponse {
    private Long stateId;
    private String stateName;
    private String stateHeadEmail;
}
