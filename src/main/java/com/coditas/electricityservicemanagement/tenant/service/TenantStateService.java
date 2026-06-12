package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantStateRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.TenantStateResponse;
import com.coditas.electricityservicemanagement.tenant.entity.TenantState;
import com.coditas.electricityservicemanagement.tenant.mappers.TenantGeographyMapper;
import com.coditas.electricityservicemanagement.tenant.repository.TenantStateRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.tenant.constants.TenantConstants.*;

@Service
@RequiredArgsConstructor
public class TenantStateService {

    private final TenantStateRepository tenantStateRepository;
    private final TenantGeographyMapper tenantGeographyMapper;

    public SingleResponse addState(TenantStateRequest stateRequest) {
        TenantState tenantState=tenantStateRepository.findByName(stateRequest.getStateName()).orElse(null);

        if(!Objects.isNull(tenantState)){
            throw new AlreadyExistException(TENANT_STATE+ALREADY_EXIST);
        }

        TenantState newTenantState=TenantState.builder()
                .name(stateRequest.getStateName())
                .build();

        tenantStateRepository.save(newTenantState);

        return SingleResponse.builder()
                .message(TENANT_STATE_CREATED)
                .build();

    }

    public TenantStateResponse getState(Long stateId) {

        TenantState tenantState=tenantStateRepository.findById(stateId)
                .orElseThrow(()->new NotFoundException(TENANT_STATE+NOT_FOUND));

        return tenantGeographyMapper.tenantStateResponse(tenantState);
    }


    public List<TenantStateResponse> getAllStates(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        Page<TenantState> tenantStateList=tenantStateRepository.findAll(pageable);

        return tenantStateList
                .stream()
                .map(tenantGeographyMapper::tenantStateResponse)
                .toList();
    }
}
