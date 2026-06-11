package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceAreaResponse {

    private String areaName;

    private String technicianEmail;
    private String billerEmail;

}
