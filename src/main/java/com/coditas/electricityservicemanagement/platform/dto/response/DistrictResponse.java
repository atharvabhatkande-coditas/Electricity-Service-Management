package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictResponse {
    private Long districtId;
    private String districtName;

    private StateResponse state;
    private String districtHeadEmail;
}
