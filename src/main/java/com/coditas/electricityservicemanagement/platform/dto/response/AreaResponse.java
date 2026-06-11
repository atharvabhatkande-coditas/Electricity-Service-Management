package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaResponse {
    private String areaName;
    private String pincode;

    private CityResponse city;
}
