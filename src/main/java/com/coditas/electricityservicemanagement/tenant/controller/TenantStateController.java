package com.coditas.electricityservicemanagement.tenant.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.StateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.StateResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantStateRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.TenantStateResponse;
import com.coditas.electricityservicemanagement.tenant.service.TenantStateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/state")
public class TenantStateController {

    private final TenantStateService tenantStateService;
    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> addState(@Valid @RequestBody TenantStateRequest stateRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(tenantStateService.addState(stateRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<TenantStateResponse>>>getAllStates(@PathVariable int pageNo){
        ApplicationResponse<List<TenantStateResponse>>applicationResponse=new ApplicationResponse<>(tenantStateService.getAllStates(pageNo));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<ApplicationResponse<TenantStateResponse>>getState(@PathVariable Long stateId){
        ApplicationResponse<TenantStateResponse>applicationResponse=new ApplicationResponse<>(tenantStateService.getState(stateId));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

}
