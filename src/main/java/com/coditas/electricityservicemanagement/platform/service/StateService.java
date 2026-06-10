package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.StateHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.StateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.StateResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.State;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.mappers.StateMapper;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import com.coditas.electricityservicemanagement.platform.repository.StateRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.*;
import static com.coditas.electricityservicemanagement.platform.constants.TenantConstants.NOT_FOUND;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.*;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private final InvitationService invitationService;
    private final PlatformUserRepository platformUserRepository;


    public SingleResponse addState(@Valid StateRequest stateRequest, PlatformUsers invitedBy) {
        State state=stateRepository.findByName(stateRequest.getName()).orElse(null);
        if(!Objects.isNull(state)){
            throw new AlreadyExistException(String.format(STATE+" "+ALREADY_EXIST));
        }

        State newState=State.builder()
                .name(stateRequest.getName())
                .stateHead(invitedBy)
                .build();
        stateRepository.save(newState);
        InvitationRequest invitationRequest=InvitationRequest.builder()
                .email(stateRequest.getStateHeadEmail())
                .role(RoleType.STATE_HEAD)
                .build();
        invitationService.sendInvitation(invitationRequest,invitedBy);
        return SingleResponse.builder()
                .message(STATE_CREATED)
                .build();
    }

    public List<StateResponse> getALlStates(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        return stateRepository.findAll(pageable)
                .stream()
                .map(stateMapper::toDto)
                .toList();
    }

    public StateResponse getState(Long stateId,PlatformUsers platformUser) {
        State state= stateRepository.findById(stateId)
                .orElseThrow(()->new NotFoundException(String.format(STATE+" "+NOT_FOUND)));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.STATE_HEAD){
            State stateHeadState=stateRepository.findByStateHead(platformUser)
                    .orElseThrow(()->new NotFoundException(STATE+NOT_FOUND));
            if(!Objects.equals(state.getId(), stateHeadState.getId())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
            return stateMapper.toDto(stateHeadState);
        }else{
            return stateMapper.toDto(state);
        }
    }

    public SingleResponse assignStateHead(StateHeadAssignRequest stateHeadAssignRequest, Long stateId) {
        State state=stateRepository.findById(stateId)
                .orElseThrow(()->new NotFoundException(STATE+" "+NOT_FOUND));

        PlatformUsers stateHead=platformUserRepository.findByUsername(stateHeadAssignRequest.getStateHeadEmail())
                .orElseThrow(()->new NotFoundException(STATE_HEAD_NOT_FOUND));


        state.setStateHead(stateHead);
        stateRepository.save(state);

        return SingleResponse.builder()
                .message(STATE_HEAD_ASSIGNED)
                .build();
    }
}
