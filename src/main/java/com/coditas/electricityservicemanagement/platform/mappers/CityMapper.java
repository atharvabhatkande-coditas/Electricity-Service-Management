package com.coditas.electricityservicemanagement.platform.mappers;

import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.City;
import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class CityMapper {

  public DistrictCityResponse districtCityResponse(District district, Page<City> cityList){
      List<CityResponseItem>cityResponseItems=cityList.stream().map(this::cityResponseItem).toList();
      return DistrictCityResponse.builder()
              .districtId(district.getId())
              .districtName(district.getName())
              .cityResponse(cityResponseItems)
              .build();

  }

  public CityResponseItem cityResponseItem(City city){
      return CityResponseItem.builder()
              .cityName(city.getName())
              .cityHeadEmail(city.getCityHead().getUsername())
              .cityId(city.getId())
              .build();
  }


  public CityResponse cityResponse(City city){
      return CityResponse.builder()
              .cityName(city.getName())
              .cityHeadEmail(city.getCityHead().getUsername())
              .cityId(city.getId())
              .build();
  }


  public StateDistrictCityResponse stateDistrictCityResponse(State state,List<District>districtList,List<City>cityList){
        List<DistrictCityResponseItem>districtCityResponseItems=districtList
                .stream()
                .map(district -> districtCityResponseItem(district,cityList
                        .stream()
                        .filter(city -> Objects.equals(city.getDistrict().getId(),district.getId()))
                        .toList()))
                .toList();

        return StateDistrictCityResponse.builder()
                .stateId(state.getId())
                .stateName(state.getName())
                .districtCityResponse(districtCityResponseItems)
                .build();
  }

  public DistrictCityResponseItem districtCityResponseItem(District district ,List<City>cities){
      List<CityResponseItem>cityResponseItems=cities.stream().map(this::cityResponseItem).toList();
      return DistrictCityResponseItem.builder()
              .districtId(district.getId())
              .districtName(district.getName())
              .districtHeadEmail(district.getDistrictHead().getUsername())
              .cityResponse(cityResponseItems)
              .build();
  }



  public List<StateDistrictCityResponse>stateDistrictCityResponseList(List<City>cities){
    Map<Long,List<City>>stateMap=cities.stream().collect(Collectors.groupingBy(city -> city.getDistrict().getState().getId()));
    return stateMap.values().stream().map(this::stateDistrictCityResponse).toList();
  }


    public StateDistrictCityResponse stateDistrictCityResponse(List<City>stateCity){
       State state=stateCity.getFirst().getDistrict().getState();

       Map<Long,List<City>>districtMap=stateCity.stream().collect(Collectors.groupingBy(city -> city.getDistrict().getId()));
        List<DistrictCityResponseItem>districtCityResponseItems=districtMap.values()
                .stream()
                .map(this::districtCityResponseItem)
                .toList();
       return StateDistrictCityResponse.builder()
               .stateId(state.getId())
               .stateName(state.getName())
               .districtCityResponse(districtCityResponseItems)
               .build();
    }

    public DistrictCityResponseItem districtCityResponseItem(List<City>districtCities){
       District district=districtCities.getFirst().getDistrict();
        List<CityResponseItem>cityResponseItems=districtCities.stream().map(this::cityResponseItem).toList();
        return DistrictCityResponseItem.builder()
                .districtId(district.getId())
                .districtName(district.getName())
                .districtHeadEmail(district.getDistrictHead().getUsername())
                .cityResponse(cityResponseItems)
                .build();
    }

}
