package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.Area;
import com.coditas.electricityservicemanagement.platform.entity.City;
import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.State;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AreaMapper {

    public AreaResponse areaResponse(Area area){
        return AreaResponse.builder()
                .areaName(area.getName())
                .pincode(area.getPincode())
                .city(CityResponse.builder()
                        .cityName(area.getCity().getName())
                        .cityHeadEmail(area.getCity().getCityHead().getUsername())
                        .cityId(area.getCity().getId())
                        .build())
                .build();
    }

    public CityAreaResponse cityAreaResponse(List<Area>areaList){
        List<AreaResponse>areaResponses=areaList.stream().map(this::areaResponse).toList();
        City city=areaList.getLast().getCity();
        return CityAreaResponse.builder()
                .cityName(city.getName())
                .areas(areaResponses)
                .build();

    }

    public DistrictCityAreaResponse districtCityAreaResponse(List<Area>areaList){
        District district=areaList.getFirst().getCity().getDistrict();
        Map<Long,List<Area>>cityAreaList=areaList.stream().collect(Collectors.groupingBy(area -> area.getCity().getId()));
        List<CityAreaResponse>cityAreaResponses=cityAreaList.values().stream().map(this::cityAreaResponse).toList();
        return DistrictCityAreaResponse.builder()
                .districtName(district.getName())
                .cities(cityAreaResponses)
                .build();
    }

    public StateDistrictCityAreaResponse stateDistrictCityAreaResponse(List<Area>areaList){
        State state=areaList.getFirst().getCity().getDistrict().getState();
        Map<Long,List<Area>>districtAreas=areaList.stream().collect(Collectors.groupingBy(area -> area.getCity().getDistrict().getId()));
        List<DistrictCityAreaResponse>districtCityAreaResponses=districtAreas.values().stream().map(this::districtCityAreaResponse).toList();
        return StateDistrictCityAreaResponse.builder()
                .stateName(state.getName())
                .districts(districtCityAreaResponses)
                .build();
    }

    public List<StateDistrictCityAreaResponse> stateDistrictCityAreaResponseList(List<Area>areas){
        Map<Long,List<Area>>stateAreas=areas.stream()
                .collect(Collectors.groupingBy(area -> area.getCity().getDistrict().getState()
                        .getId()));

        return stateAreas.values()
                .stream()
                .map(this::stateDistrictCityAreaResponse)
                .toList();
    }
}
