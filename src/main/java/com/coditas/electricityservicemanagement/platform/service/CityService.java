package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.CityHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.CityRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.CityResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.DistrictCityResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.StateDistrictCityResponse;
import com.coditas.electricityservicemanagement.platform.entity.City;
import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.State;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.mappers.CityMapper;
import com.coditas.electricityservicemanagement.platform.repository.CityRepository;
import com.coditas.electricityservicemanagement.platform.repository.DistrictRepository;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import com.coditas.electricityservicemanagement.platform.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.*;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.*;
import static com.coditas.electricityservicemanagement.platform.constants.TenantConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final InvitationService invitationService;
    private final CityMapper cityMapper;
    private final PlatformUserRepository platformUserRepository;
    private final StateRepository stateRepository;


    public SingleResponse addCity(CityRequest cityRequest, PlatformUsers districtHead) {
        City city=cityRepository.findByName(cityRequest.getCityName())
                .orElse(null);
        if(!Objects.isNull(city)){
            throw new AlreadyExistException(String.format(CITY+" "+ALREADY_EXIST));
        }
        District district=districtRepository.findById(cityRequest.getDistrictId())
                .orElseThrow(()->new NotFoundException(DISTRICT+NOT_FOUND));
        if(!Objects.equals(district.getDistrictHead().getUsername(),districtHead.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        City newCity=City.builder()
                        .name(cityRequest.getCityName())
                .district(district)
                .cityHead(districtHead)
                .build();

        cityRepository.save(newCity);

        InvitationRequest invitationRequest=InvitationRequest.builder()
                .email(cityRequest.getCityHeadEmail())
                .role(RoleType.CITY_HEAD)
                .build();

        invitationService.sendInvitation(invitationRequest,districtHead);
        return SingleResponse.builder()
                .message(CITY_CREATED)
                .build();
    }


    //get allStates->allDistricts->allCity
    public List<StateDistrictCityResponse> getALlCityOfAllStates(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        Page<City>cities=cityRepository.findAll(pageable);
        return cityMapper.stateDistrictCityResponseList(cities.getContent());
    }



    //get single city
    public CityResponse getCity(Long cityId, PlatformUsers platformUser) {
        City city= cityRepository.findById(cityId)
                .orElseThrow(()->new NotFoundException(String.format(CITY+" "+NOT_FOUND)));

        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.CITY_HEAD){
            if(!Objects.equals(city.getCityHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }

        if(higherRole==RoleType.DISTRICT_HEAD){
            if(!Objects.equals(city.getDistrict().getDistrictHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }

        if(higherRole==RoleType.STATE_HEAD){
            if(!Objects.equals(city.getDistrict().getState().getStateHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }
        return cityMapper.cityResponse(city);

    }
   //get state->districts->city
    public StateDistrictCityResponse getALlCityOfState(Long stateId, int pageNo, PlatformUsers platformUser) {
        Pageable pageable= PageRequest.of(pageNo,5);
        State state= stateRepository.findById(stateId)
                .orElseThrow(()->new NotFoundException(String.format(STATE+" "+NOT_FOUND)));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.STATE_HEAD){
            if(!Objects.equals(state.getStateHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }
        Page<District>districtList=districtRepository.findByState_Id(stateId,pageable);
        Page<City>cityList=cityRepository.findByDistrictIn(districtList.getContent(),pageable);
        return cityMapper.stateDistrictCityResponse(state,districtList.getContent(),cityList.getContent());
    }


    //getCities of  Districts
    public DistrictCityResponse getCitiesOfDistrict(Long districtId,int pageNo,PlatformUsers platformUser){
        District district=districtRepository.findById(districtId)
                .orElseThrow(()->new NotFoundException(DISTRICT+NOT_FOUND));

        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.DISTRICT_HEAD){
            if(!Objects.equals(district.getDistrictHead().getUsername(),platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }

        if(higherRole==RoleType.STATE_HEAD){
            if(!Objects.equals(district.getState().getStateHead().getUsername(),platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }

        Pageable pageable= PageRequest.of(pageNo,5);
        Page<City>cityList=cityRepository.findByDistrict_Id(districtId,pageable);

        return cityMapper.districtCityResponse(district,cityList);
    }


    public SingleResponse assignCityHead(CityHeadAssignRequest cityHeadAssignRequest, Long cityId, PlatformUsers districtHead) {
        City city=cityRepository.findById(cityId)
                .orElseThrow(()->new NotFoundException(CITY+" "+NOT_FOUND));

        District district=districtRepository.findByDistrictHead(districtHead)
                .orElseThrow(()->new AuthenticationException(UNAUTHORIZED));

        if(!Objects.equals(city.getDistrict().getId(),district.getId())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        PlatformUsers cityHeadAccount=platformUserRepository.findByUsername(cityHeadAssignRequest.getCityHeadEmail())
                .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));


        city.setCityHead(cityHeadAccount);
        cityRepository.save(city);

        return SingleResponse.builder()
                .message(CITY_HEAD_ASSIGNED)
                .build();
    }


}
