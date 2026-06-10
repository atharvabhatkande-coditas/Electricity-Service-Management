package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponseItem{
	private String cityName;
	private String cityHeadEmail;
	private Long cityId;
}