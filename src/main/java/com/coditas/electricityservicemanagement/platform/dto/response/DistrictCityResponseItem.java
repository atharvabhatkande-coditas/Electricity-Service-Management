package com.coditas.electricityservicemanagement.platform.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictCityResponseItem{
	private Long districtId;
	private String districtName;
	private String districtHeadEmail;
	private List<CityResponseItem> cityResponse;
}