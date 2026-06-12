package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.CityServiceAreaResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.DistrictCityServiceAreaResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ServiceAreaResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.StateDistrictCityServiceAreaResponse;
import com.coditas.electricityservicemanagement.platform.entity.City;
import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.ServiceArea;
import com.coditas.electricityservicemanagement.platform.entity.State;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ServiceAreaMapper {

    public ServiceAreaResponse serviceAreaResponse(ServiceArea serviceArea){
        return ServiceAreaResponse.builder()
                .areaName(serviceArea.getArea().getName())
                .technicianEmail(serviceArea.getTechnician().getUsername())
                .billerEmail(serviceArea.getBiller().getUsername())
                .build();
    }

    public CityServiceAreaResponse cityServiceAreaResponse(List<ServiceArea> serviceAreaList){
        City city=serviceAreaList.getFirst().getArea().getCity();
        List<ServiceAreaResponse>serviceAreaResponses=serviceAreaList.stream().map(this::serviceAreaResponse).toList();
        return CityServiceAreaResponse.builder()
                .cityName(city.getName())
                .serviceAreas(serviceAreaResponses)
                .build();
    }

    public DistrictCityServiceAreaResponse districtCityServiceAreaResponse(List<ServiceArea>serviceAreaList){
        Map<Long,List<ServiceArea>>cityServiceAreas=serviceAreaList
                .stream()
                .collect(Collectors.groupingBy(serviceArea -> serviceArea.getArea().getId()));
        District district=serviceAreaList.getFirst()
                .getArea()
                .getCity()
                .getDistrict();
        List<CityServiceAreaResponse>cityServiceAreaResponsesList=cityServiceAreas.values().stream().map(this::cityServiceAreaResponse).toList();
        return DistrictCityServiceAreaResponse.builder()
                .districtName(district.getName())
                .cities(cityServiceAreaResponsesList)
                .build();
    }


    public StateDistrictCityServiceAreaResponse stateDistrictCityServiceAreaResponse(List<ServiceArea>serviceAreaList){
        Map<Long,List<ServiceArea>>stateServiceAreas=serviceAreaList.stream().collect(Collectors.groupingBy(serviceArea -> serviceArea.getArea().getCity().getDistrict().getId()));
        List<DistrictCityServiceAreaResponse>districtCityServiceAreaResponseList=stateServiceAreas.values().stream().map(serviceAreaList1 -> districtCityServiceAreaResponse(serviceAreaList)).toList();
        State state=serviceAreaList.getFirst().getArea().getCity().getDistrict().getState();
        return StateDistrictCityServiceAreaResponse.builder()
                .stateName(state.getName())
                .districts(districtCityServiceAreaResponseList)
                .build();
    }

    public List<StateDistrictCityServiceAreaResponse>stateDistrictCityServiceAreaResponseList(List<ServiceArea>serviceAreaList){

       Map<Long,List<ServiceArea>>stateServiceAreas=serviceAreaList.stream().collect(Collectors.groupingBy(serviceArea -> serviceArea.getArea().getCity().getDistrict().getState().getId()));

       return stateServiceAreas.values()
               .stream()
               .map(this::stateDistrictCityServiceAreaResponse)
               .toList();
    }
}
