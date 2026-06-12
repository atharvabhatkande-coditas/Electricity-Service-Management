package com.coditas.electricityservicemanagement.tenant.service;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterBillingPlanRequest;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterBillingPlanUpdateRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.MeterBillingPlanResponse;
import com.coditas.electricityservicemanagement.tenant.entity.MeterBillingPlan;
import com.coditas.electricityservicemanagement.tenant.entity.MeterType;
import com.coditas.electricityservicemanagement.tenant.mappers.MeterBillingPlanMapper;
import com.coditas.electricityservicemanagement.tenant.repository.MeterBillingPlanRepository;
import com.coditas.electricityservicemanagement.tenant.repository.MeterTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_FOUND;
import static com.coditas.electricityservicemanagement.tenant.constants.TenantConstants.*;

@Service
@RequiredArgsConstructor
public class MeterBillingPlanService {
    private final MeterBillingPlanRepository meterBillingPlanRepository;
    private final MeterTypeRepository meterTypeRepository;
    private final MeterBillingPlanMapper meterBillingPlanMapper;

    public SingleResponse addMeterBillingPlan(MeterBillingPlanRequest meterBillingPlanRequest) {
        MeterType meterType=meterTypeRepository.findById(meterBillingPlanRequest.getMeterTypeId())
                .orElseThrow(()->new NotFoundException(METER_TYPE+NOT_FOUND));

        MeterBillingPlan meterBillingPlan=meterBillingPlanRepository.findByMeterType_Id(meterBillingPlanRequest.getMeterTypeId())
                .orElse(null);
        if(!Objects.isNull(meterBillingPlan)){
            throw new AlreadyExistException(METER_BILLING_PLAN+ALREADY_EXIST);
        }
        MeterBillingPlan newMeterBillingPlan=MeterBillingPlan.builder()
                .meterType(meterType)
                .ratePerUnit(meterBillingPlanRequest.getRatePerUnit())
                .fixedCharge(meterBillingPlanRequest.getFixedCharge())
                .photosRequired(meterBillingPlanRequest.getPhotosRequired())
                .photosInterval(meterBillingPlanRequest.getPhotosInterval())
                .build();

        meterBillingPlanRepository.save(newMeterBillingPlan);

        return SingleResponse.builder()
                .message(METER_BILLING_PLAN_ADDED)
                .build();
    }

    public SingleResponse updateMeterBillingPlan(MeterBillingPlanUpdateRequest meterBillingPlanUpdateRequest) {

        MeterType meterType=meterTypeRepository.findById(meterBillingPlanUpdateRequest.getMeterTypeId())
                .orElseThrow(()->new NotFoundException(METER_TYPE+NOT_FOUND));
        MeterBillingPlan meterBillingPlan=meterBillingPlanRepository.findByMeterType_Id(meterBillingPlanUpdateRequest.getMeterTypeId())
                .orElseThrow(()->new NotFoundException(METER_BILLING_PLAN+NOT_FOUND));

        if(!Objects.isNull(meterBillingPlanUpdateRequest.getRatePerUnit())){
            meterBillingPlan.setRatePerUnit(meterBillingPlanUpdateRequest.getRatePerUnit());
        }
        if(!Objects.isNull(meterBillingPlanUpdateRequest.getFixedCharge())){
            meterBillingPlan.setFixedCharge(meterBillingPlanUpdateRequest.getFixedCharge());
        }

        if(!Objects.isNull(meterBillingPlanUpdateRequest.getPhotosRequired())){
            meterBillingPlan.setPhotosRequired(meterBillingPlanUpdateRequest.getPhotosRequired());
        }

        if(!Objects.isNull(meterBillingPlanUpdateRequest.getPhotosInterval())){
            meterBillingPlan.setPhotosInterval(meterBillingPlanUpdateRequest.getPhotosInterval());
        }

        meterBillingPlanRepository.save(meterBillingPlan);

        return SingleResponse.builder()
                .message(METER_BILLING_PLAN_UPDATED)
                .build();
    }

    public MeterBillingPlanResponse getMeterBillingPlan(Long meterTypeId) {

        MeterBillingPlan meterBillingPlan=meterBillingPlanRepository.findByMeterType_Id(meterTypeId)
                .orElseThrow(()->new NotFoundException(METER_BILLING_PLAN+NOT_FOUND));

        return meterBillingPlanMapper.meterBillingPlanResponse(meterBillingPlan);
    }

    public List<MeterBillingPlanResponse> getAllMeterBillingPlan(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        return meterBillingPlanRepository.findAll(pageable)
                .stream()
                .map(meterBillingPlanMapper::meterBillingPlanResponse)
                .toList();
    }
}
