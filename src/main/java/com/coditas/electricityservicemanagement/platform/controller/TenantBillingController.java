package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.GenerateBillRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.TenantBillingStatusUpdateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.AllTenantBillingResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.TenantBillingResponse;
import com.coditas.electricityservicemanagement.platform.service.TenantBillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/tenant-billing")
public class TenantBillingController {
    private final TenantBillingService tenantBillingService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>generateBill(@Valid @RequestBody GenerateBillRequest generateBillRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(tenantBillingService.generateBill(generateBillRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/tenant/{tenantId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<TenantBillingResponse>>>getAllBillsOfTenant(@PathVariable String tenantId,@PathVariable int pageNo){
        ApplicationResponse<List<TenantBillingResponse>>applicationResponse=new ApplicationResponse<>(tenantBillingService.getALlBillsOfTenant(tenantId,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<AllTenantBillingResponse>>>getAllBillsOfAllTenants(@PathVariable int pageNo){
        ApplicationResponse<List<AllTenantBillingResponse>>applicationResponse=new ApplicationResponse<>(tenantBillingService.getALlBillsOfAllTenant(pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PatchMapping("/status/{billingId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>>updateBillingStatus(@PathVariable Long billingId, @RequestBody TenantBillingStatusUpdateRequest tenantBillingStatusUpdateRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(tenantBillingService.updateBillStatus(billingId,tenantBillingStatusUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }
}
