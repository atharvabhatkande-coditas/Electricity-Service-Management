package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.DistrictHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.DistrictRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.StateHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.DistrictResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.StateResponse;
import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.State;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.mappers.DistrictMapper;
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
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final InvitationService invitationService;
    private final DistrictMapper districtMapper;
    private final PlatformUserRepository platformUserRepository;

    public SingleResponse addDistrict(DistrictRequest districtRequest, PlatformUsers stateHead) {
        District district=districtRepository.findByName(districtRequest.getDistrictName()).orElse(null);
        State state=stateRepository.findById(districtRequest.getStateId())
                .orElseThrow(()->new NotFoundException(STATE+NOT_FOUND));
        if(!Objects.equals(state.getStateHead().getUsername(),stateHead.getUsername())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        if(!Objects.isNull(district)){
            throw new AlreadyExistException(String.format(DISTRICT+" "+ALREADY_EXIST));
        }

        District newDistrict=District.builder()
                .name(districtRequest.getDistrictName())
                .state(state)
                .districtHead(stateHead)
                .build();

        districtRepository.save(newDistrict);
        InvitationRequest invitationRequest=InvitationRequest.builder()
                .email(districtRequest.getDistrictHeadEmail())
                .role(RoleType.DISTRICT_HEAD)
                .build();

        invitationService.sendInvitation(invitationRequest,stateHead);
        return SingleResponse.builder()
                .message(DISTRICT_CREATED)
                .build();
    }


    public List<DistrictResponse> getALlDistricts(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        return districtRepository.findAll(pageable)
                .stream()
                .map(districtMapper::toDto)
                .toList();
    }

    public DistrictResponse getDistrict(Long districtId,PlatformUsers platformUser) {
        District district= districtRepository.findById(districtId)
                .orElseThrow(()->new NotFoundException(String.format(DISTRICT+" "+NOT_FOUND)));

        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.DISTRICT_HEAD){
            District districtHeadDistrict=districtRepository.findByDistrictHead(platformUser)
                    .orElseThrow(()->new AuthenticationException(UNAUTHORIZED));

            if(!Objects.equals(district.getId(), districtHeadDistrict.getId())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
            return districtMapper.toDto(district);
        }else{
            return districtMapper.toDto(district);
        }
    }

    public SingleResponse assignDistrictHead(DistrictHeadAssignRequest districtHeadAssignRequest, Long districtId,PlatformUsers stateHead) {
        District district=districtRepository.findById(districtId)
                .orElseThrow(()->new NotFoundException(DISTRICT+" "+NOT_FOUND));

        State state=stateRepository.findByStateHead(stateHead)
                .orElseThrow(()->new AuthenticationException(UNAUTHORIZED));

        if(!Objects.equals(district.getState().getId(),state.getId())){
            throw new AuthenticationException(UNAUTHORIZED);
        }

        PlatformUsers districtHead=platformUserRepository.findByUsername(districtHeadAssignRequest.getDistrictHeadEmail())
                .orElseThrow(()->new NotFoundException(DISTRICT_HEAD_NOT_FOUND));


        district.setDistrictHead(districtHead);
        districtRepository.save(district);

        return SingleResponse.builder()
                .message(DISTRICT_HEAD_ASSIGNED)
                .build();
    }

    public List<DistrictResponse> getALlDistrictsOfState(Long stateId, int pageNo,PlatformUsers platformUser) {
        Pageable pageable= PageRequest.of(pageNo,5);
        State state= stateRepository.findById(stateId)
                .orElseThrow(()->new NotFoundException(String.format(STATE+" "+NOT_FOUND)));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.STATE_HEAD){
            State stateHeadState=stateRepository.findByStateHead(platformUser)
                    .orElseThrow(()->new AuthenticationException(UNAUTHORIZED));
            if(!Objects.equals(state.getId(), stateHeadState.getId())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
            Page<District> districtList=districtRepository.findByState_Id(stateHeadState.getId(),pageable);
            return districtList.stream().map(districtMapper::toDto).toList();
        }else{
            Page<District>districtList=districtRepository.findByState_Id(stateId,pageable);
            return districtList.stream().map(districtMapper::toDto).toList();
        }
    }
}
