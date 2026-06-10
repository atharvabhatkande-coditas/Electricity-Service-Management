package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.GenerateBillRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.TenantBillingStatusUpdateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.AllTenantBillingResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.TenantBillingResponse;
import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import com.coditas.electricityservicemanagement.platform.entity.TenantBilling;
import com.coditas.electricityservicemanagement.platform.enums.BillingStatus;
import com.coditas.electricityservicemanagement.platform.mappers.TenantBillingMapper;
import com.coditas.electricityservicemanagement.platform.repository.TenantBillingRepository;
import com.coditas.electricityservicemanagement.platform.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_FOUND;
import static com.coditas.electricityservicemanagement.platform.constants.TenantConstants.*;


@Service
@RequiredArgsConstructor
public class TenantBillingService {
    private final TenantBillingRepository tenantBillingRepository;
    private final TenantRepository tenantRepository;
    private final TenantBillingMapper tenantBillingMapper;

    @Transactional
    public SingleResponse generateBill(GenerateBillRequest generateBillRequest) {
        Tenant tenant=tenantRepository.findById(generateBillRequest.getTenantId())
                .orElseThrow(()->new NotFoundException(TENANT+NOT_FOUND));
        TenantBilling tenantBilling=TenantBilling.builder()
                .amount(generateBillRequest.getAmount())
                .dueDate(generateBillRequest.getDueDate())
                .billingStatus(BillingStatus.PENDING)
                .billingDate(LocalDateTime.now())
                .tenant(tenant)
                .build();


        tenantBillingRepository.save(tenantBilling);
        return SingleResponse.builder().message(BILL_GENERATED).build();

    }

    public List<TenantBillingResponse> getALlBillsOfTenant(String tenantId,int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);
        Tenant tenant=tenantRepository.findById(tenantId).orElseThrow(()->new NotFoundException(TENANT+" "+NOT_FOUND));
        Page<TenantBilling> tenantBillings=tenantBillingRepository.findByTenant_Id(tenantId,pageable);

        return tenantBillings.stream()
                .map(tenantBillingMapper::tenantBillingResponse)
                .toList();
    }

    public List<AllTenantBillingResponse> getALlBillsOfAllTenant(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,5);

        Page<TenantBilling>tenantBillings=tenantBillingRepository.findAll(pageable);
        Map<String,List<TenantBilling>>longListMap=tenantBillings
                .getContent()
                .stream()
                .collect(Collectors
                        .groupingBy(tenantBilling -> tenantBilling.getTenant().getId()));
        return longListMap.values().stream().map(tenantBillingMapper::allTenantBillingResponse).toList();
    }

    public SingleResponse updateBillStatus(Long billingId, TenantBillingStatusUpdateRequest tenantBillingStatusUpdateRequest) {
        TenantBilling tenantBilling=tenantBillingRepository.findById(billingId)
                .orElseThrow(()->new NotFoundException(TENANT_BILL+NOT_FOUND));
       tenantBilling.setBillingStatus(tenantBillingStatusUpdateRequest.getBillingStatus());
       tenantBillingRepository.save(tenantBilling);
       return SingleResponse.builder()
               .message(TENANT_BILL_STATUS_UPDATED)
               .build();
    }
}
