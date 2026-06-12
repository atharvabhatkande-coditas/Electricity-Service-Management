package com.coditas.electricityservicemanagement.tenant.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterBillingPlanRequest;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterBillingPlanUpdateRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.MeterBillingPlanResponse;
import com.coditas.electricityservicemanagement.tenant.service.MeterBillingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/meter-billing-plan")
public class MeterBillingPlanController {
    private final MeterBillingPlanService meterBillingPlanService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> addMeterBillingPlan(@RequestBody MeterBillingPlanRequest meterBillingPlanRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(meterBillingPlanService.addMeterBillingPlan(meterBillingPlanRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PatchMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>updateMeterBillingPlan(@RequestBody MeterBillingPlanUpdateRequest meterBillingPlanUpdateRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(meterBillingPlanService.updateMeterBillingPlan(meterBillingPlanUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }
    @GetMapping("/{meterTypeId}")
    public ResponseEntity<ApplicationResponse<MeterBillingPlanResponse>>getMeterBillingPlan(@PathVariable Long meterTypeId){
        ApplicationResponse<MeterBillingPlanResponse>applicationResponse=new ApplicationResponse<>(meterBillingPlanService.getMeterBillingPlan(meterTypeId));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<MeterBillingPlanResponse>>>getAllMeterBillingPlan(@PathVariable int pageNo){
        ApplicationResponse<List<MeterBillingPlanResponse>>applicationResponse=new ApplicationResponse<>(meterBillingPlanService.getAllMeterBillingPlan(pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }
}
