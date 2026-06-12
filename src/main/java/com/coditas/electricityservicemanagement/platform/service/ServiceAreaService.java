package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.AssignBillerRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.AssignTechnicianRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.ServiceAreaAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.ServiceAreaRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.*;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.enums.ServiceType;
import com.coditas.electricityservicemanagement.platform.mappers.ServiceAreaMapper;
import com.coditas.electricityservicemanagement.platform.repository.*;
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
public class ServiceAreaService {
    private final ServiceAreaRepository serviceAreaRepository;
    private final AreaRepository areaRepository;
    private final PlatformUserRepository platformUserRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;

    private final ServiceAreaMapper serviceAreaMapper;


    public SingleResponse addServiceArea(ServiceAreaRequest serviceAreaRequest, PlatformUsers platformUser) {
        ServiceArea serviceArea=serviceAreaRepository.findByArea_Id(serviceAreaRequest.getAreaId())
                .orElse(null);
        Area area=areaRepository.findById(serviceAreaRequest.getAreaId())
                .orElseThrow(()->new NotFoundException(AREA+NOT_FOUND));

        if(!Objects.isNull(serviceArea)){
            throw new NotFoundException(SERVICE_AREA+ALREADY_EXIST);
        }
        ServiceArea newServiceArea=ServiceArea.builder()
                .technician(platformUser)
                .biller(platformUser)
                .area(area)
                .build();

            serviceAreaRepository.save(newServiceArea);
            return SingleResponse.builder()
                    .message(SERVICE_AREA_CREATED)
                    .build();
    }

    public SingleResponse assignTechnicianOrBiller(ServiceAreaAssignRequest serviceAreaAssignRequest) {
       ServiceArea serviceArea=serviceAreaRepository.findById(serviceAreaAssignRequest.getServiceAreaId())
               .orElseThrow(()->new NotFoundException(SERVICE_AREA+NOT_FOUND));
      if(!Objects.isNull(serviceAreaAssignRequest.getTechnicianEmail())){
          PlatformUsers technician=platformUserRepository.findByUsername(serviceAreaAssignRequest.getTechnicianEmail())
                  .orElseThrow(()->new NotFoundException(TECHNICIAN_NOT_FOUND));
           serviceArea.setTechnician(technician);
      }
        if(!Objects.isNull(serviceAreaAssignRequest.getBillerEmail())){
            PlatformUsers biller=platformUserRepository.findByUsername(serviceAreaAssignRequest.getBillerEmail())
                    .orElseThrow(()->new NotFoundException(BILLER_NOT_FOUND));
            serviceArea.setBiller(biller);
        }

        serviceAreaRepository.save(serviceArea);

        return SingleResponse.builder()
                .message(TECHNICIAN_ASSIGNED)
                .build();
    }


    public ServiceAreaResponse getServiceArea(Long serviceAreaId, PlatformUsers platformUser) {

        ServiceArea serviceArea=serviceAreaRepository.findById(serviceAreaId)
                .orElseThrow(()->new NotFoundException(SERVICE_AREA+NOT_FOUND));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());

        if(higherRole==RoleType.CITY_HEAD && !Objects.equals(serviceArea.getArea().getCity().getCityHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        if(higherRole==RoleType.DISTRICT_HEAD && !Objects.equals(serviceArea.getArea().getCity().getDistrict().getDistrictHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }
        if(higherRole==RoleType.STATE_HEAD && !Objects.equals(serviceArea.getArea().getCity().getDistrict().getState().getStateHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        return serviceAreaMapper.serviceAreaResponse(serviceArea);

    }

    public CityServiceAreaResponse getServiceAreaOfCity(Long cityId, PlatformUsers platformUser, int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);

        City city=cityRepository.findById(cityId)
                .orElseThrow(()->new NotFoundException(CITY+NOT_FOUND));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.CITY_HEAD && !Objects.equals(city.getCityHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        if(higherRole==RoleType.DISTRICT_HEAD && !Objects.equals(city.getDistrict().getDistrictHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }
        if(higherRole==RoleType.STATE_HEAD && !Objects.equals(city.getDistrict().getState().getStateHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }
        Page<Area> areas=areaRepository.findByCity_Id(cityId,pageable);

        Page<ServiceArea>serviceAreaList=serviceAreaRepository.findByAreaIn(areas.getContent(),pageable);

        return serviceAreaMapper.cityServiceAreaResponse(serviceAreaList.getContent());
    }

    public DistrictCityServiceAreaResponse getServiceAreaOfDistrict(Long districtId, PlatformUsers platformUser, int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        District district=districtRepository.findById(districtId)
                .orElseThrow(()->new NotFoundException(DISTRICT+NOT_FOUND));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());

        if(higherRole==RoleType.DISTRICT_HEAD && !Objects.equals(district.getDistrictHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }
        if(higherRole==RoleType.STATE_HEAD && !Objects.equals(district.getState().getStateHead().getUsername(), platformUser.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }
        Page<City>cities=cityRepository.findByDistrict_Id(districtId,pageable);

        Page<Area>areas=areaRepository.findByCityIn(cities.getContent(),pageable);
        Page<ServiceArea>serviceAreas=serviceAreaRepository.findByAreaIn(areas.getContent(),pageable);

        return serviceAreaMapper.districtCityServiceAreaResponse(serviceAreas.getContent());
    }

    public StateDistrictCityServiceAreaResponse getServiceAreaOfState(Long stateId, PlatformUsers platformUser, int pageNo) {
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
        Page<ServiceArea>serviceAreas=serviceAreaRepository.findByAreaIn(areas.getContent(),pageable);

        return serviceAreaMapper.stateDistrictCityServiceAreaResponse(serviceAreas.getContent());
    }

    public List<StateDistrictCityServiceAreaResponse> getServiceAreaOfAllState(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        Page<State>states=stateRepository.findAll(pageable);
        Page<District>districts=districtRepository.findByStateIn(states.getContent(),pageable);
        Page<City>cities=cityRepository.findByDistrictIn(districts.getContent(),pageable);
        Page<Area>areas=areaRepository.findByCityIn(cities.getContent(),pageable);
        Page<ServiceArea>serviceAreas=serviceAreaRepository.findByAreaIn(areas.getContent(),pageable);
        return serviceAreaMapper.stateDistrictCityServiceAreaResponseList(serviceAreas.getContent());
    }
}
