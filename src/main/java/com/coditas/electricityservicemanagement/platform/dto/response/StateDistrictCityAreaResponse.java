package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateDistrictCityAreaResponse {
    private String stateName;
    private List<DistrictCityAreaResponse>districts;
}
