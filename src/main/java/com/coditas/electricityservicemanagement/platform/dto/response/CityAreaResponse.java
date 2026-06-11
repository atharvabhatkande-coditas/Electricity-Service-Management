package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityAreaResponse {
    private String cityName;
    private List<AreaResponse>areas;
}
