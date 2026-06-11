package com.coditas.electricityservicemanagement.platform.service;
import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.AreaRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.*;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.mappers.AreaMapper;
import com.coditas.electricityservicemanagement.platform.repository.AreaRepository;
import com.coditas.electricityservicemanagement.platform.repository.CityRepository;
import com.coditas.electricityservicemanagement.platform.repository.DistrictRepository;
import com.coditas.electricityservicemanagement.platform.repository.StateRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.*;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.UNAUTHORIZED;
import static com.coditas.electricityservicemanagement.platform.constants.TenantConstants.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final CityRepository cityRepository;
    private final AreaMapper areaMapper;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;

    public SingleResponse addArea(@Valid AreaRequest areaRequest, PlatformUsers cityHead) {
        Area area=areaRepository.findByName(areaRequest.getAreaName())
                .orElse(null);
        if(!Objects.isNull(area)){
            throw new AlreadyExistException(String.format(AREA+" "+ALREADY_EXIST));
        }
        City city=cityRepository.findById(areaRequest.getCityId())
                .orElseThrow(()->new NotFoundException(CITY+NOT_FOUND));
        if(!Objects.equals(city.getCityHead().getUsername(),cityHead.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        Area newArea=Area.builder()
                .name(areaRequest.getAreaName())
                .city(city)
                .pincode(areaRequest.getPincode())
                .build();

        areaRepository.save(newArea);
        return SingleResponse.builder()
                .message(AREA_CREATED)
                .build();
    }

    public AreaResponse getArea(Long areaId, PlatformUsers platformUser) {

        Area area=areaRepository.findById(areaId)
                .orElseThrow(()->new NotFoundException(AREA+NOT_FOUND));


        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.CITY_HEAD){
            if(!Objects.equals(area.getCity().getCityHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }

        if(higherRole==RoleType.DISTRICT_HEAD){
            if(!Objects.equals(area.getCity().getDistrict().getDistrictHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }

        if(higherRole==RoleType.STATE_HEAD){
            if(!Objects.equals(area.getCity().getDistrict().getState().getStateHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }
        return areaMapper.areaResponse(area);
    }

    public CityAreaResponse getAreaOfCity(Long cityId, PlatformUsers platformUser,int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);

        City city=cityRepository.findById(cityId)
                .orElseThrow(()->new NotFoundException(CITY+NOT_FOUND));
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
        Page<Area> areaList=areaRepository.findByCity_Id(cityId,pageable);

        if(areaList.isEmpty()){
            throw new NotFoundException(AREA+NOT_FOUND);
        }
        return areaMapper.cityAreaResponse(areaList.getContent());
    }

    public DistrictCityAreaResponse getAreaOfDistrict(Long districtId, PlatformUsers platformUser, int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);

        District district=districtRepository.findById(districtId)
                .orElseThrow(()->new NotFoundException(DISTRICT+NOT_FOUND));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());

        if(higherRole==RoleType.DISTRICT_HEAD && !Objects.equals(district.getDistrictHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
        }

        if(higherRole==RoleType.STATE_HEAD &&  !Objects.equals(district.getState().getStateHead().getUsername(), platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
        }
        Page<City>cities=cityRepository.findByDistrict_Id(districtId,pageable);
        Page<Area>areaList=areaRepository.findByCityIn(cities.getContent(),pageable);
        return areaMapper.districtCityAreaResponse(areaList.getContent());

    }

    public StateDistrictCityAreaResponse getAreaOfState(Long stateId, PlatformUsers platformUser, int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);

        State state=stateRepository.findById(stateId)
                .orElseThrow(()->new NotFoundException(STATE+NOT_FOUND));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.STATE_HEAD && !Objects.equals(state.getStateHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        Page<District>districts=districtRepository.findByState_Id(stateId,pageable);
        Page<City>cities=cityRepository.findByDistrictIn(districts.getContent(),pageable);
        Page<Area>areas=areaRepository.findByCityIn(cities.getContent(),pageable);

        return areaMapper.stateDistrictCityAreaResponse(areas.getContent());
    }

    public List<StateDistrictCityAreaResponse> getAreaOfAllState(PlatformUsers platformUser, int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);

        Page<State>states=stateRepository.findAll(pageable);
        Page<District>districts=districtRepository.findByStateIn(states.getContent(),pageable);
        Page<City>cities=cityRepository.findByDistrictIn(districts.getContent(),pageable);
        Page<Area>areas=areaRepository.findByCityIn(cities.getContent(),pageable);

        return areaMapper.stateDistrictCityAreaResponseList(areas.getContent());

    }
}
