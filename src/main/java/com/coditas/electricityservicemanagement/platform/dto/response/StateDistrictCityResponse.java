package com.coditas.electricityservicemanagement.platform.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StateDistrictCityResponse{
	private Long stateId;
	private String stateName;
	private List<DistrictCityResponseItem> districtCityResponse;
}