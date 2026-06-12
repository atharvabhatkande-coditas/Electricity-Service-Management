package com.coditas.electricityservicemanagement.tenant.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeterTypeResponse {

    private String meterTypeName;
    private String description;
}
