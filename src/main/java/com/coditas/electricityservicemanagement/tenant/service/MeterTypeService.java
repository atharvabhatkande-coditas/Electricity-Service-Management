package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterTypeRequest;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterUpdateRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.MeterTypeResponse;
import com.coditas.electricityservicemanagement.tenant.entity.MeterType;
import com.coditas.electricityservicemanagement.tenant.mappers.MeterTypeMapper;
import com.coditas.electricityservicemanagement.tenant.repository.MeterTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.ALREADY_EXIST;
import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_FOUND;
import static com.coditas.electricityservicemanagement.tenant.constants.TenantConstants.*;

@Service
@RequiredArgsConstructor
public class MeterTypeService {
    private final MeterTypeRepository meterTypeRepository;
    private final MeterTypeMapper meterTypeMapper;

    public SingleResponse addMeterType(MeterTypeRequest meterTypeRequest) {
        MeterType meterType=meterTypeRepository.findByName(meterTypeRequest.getMeterName()).orElse(null);

        if(!Objects.isNull(meterType)){
            throw new AlreadyExistException(METER_TYPE+ALREADY_EXIST);
        }

        MeterType newMeterType=MeterType.builder()
                .name(meterTypeRequest.getMeterName())
                .description(meterTypeRequest.getDescription())
                .build();

        meterTypeRepository.save(newMeterType);

        return SingleResponse.builder()
                .message(METER_TYPE_CREATED)
                .build();

    }

    public SingleResponse updateMeterType(MeterUpdateRequest meterUpdateRequest) {

        MeterType meterType=meterTypeRepository.findById(meterUpdateRequest.getMeterTypeId())
                .orElseThrow(()->new NotFoundException(METER_TYPE+NOT_FOUND));

        if(!Objects.isNull(meterUpdateRequest.getMeterName())){
            meterType.setName(meterUpdateRequest.getMeterName());
        }
        if(!Objects.isNull(meterUpdateRequest.getDescription())){
            meterType.setDescription(meterUpdateRequest.getDescription());
        }
        meterTypeRepository.save(meterType);
        return SingleResponse.builder()
                .message(METER_TYPE_UPDATED)
                .build();

    }

    public MeterTypeResponse getMeterType(Long meterTypeId) {
        MeterType meterType=meterTypeRepository.findById(meterTypeId)
                .orElseThrow(()->new NotFoundException(METER_TYPE+NOT_FOUND));

        return meterTypeMapper.meterTypeResponse(meterType);

    }

    public List<MeterTypeResponse> getAllMeterType(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        return meterTypeRepository.findAll(pageable)
                .stream()
                .map(meterTypeMapper::meterTypeResponse)
                .toList();
    }
}
