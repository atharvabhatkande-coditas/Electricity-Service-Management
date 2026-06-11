package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityServiceAreaResponse {

    private String cityName;
    private List<ServiceAreaResponse>serviceAreas;
}
