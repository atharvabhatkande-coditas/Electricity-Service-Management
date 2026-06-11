package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictCityAreaResponse {
    private String districtName;
    private List<CityAreaResponse>cities;
}
