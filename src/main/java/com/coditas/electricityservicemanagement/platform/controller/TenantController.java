package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.TenantOnBoardRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.TenantUpdateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.TenantResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.UpdateResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.TenantService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/tenant")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;


    @PostMapping("/onboard")
    public ResponseEntity<ApplicationResponse<SingleResponse>> onBoardTenant(@Valid @RequestBody TenantOnBoardRequest tenant, @AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(tenantService.onBoardTenant(tenant,platformUser));
        return  ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping()
    public ResponseEntity<ApplicationResponse<List<TenantResponse>>> getAllTenants(@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<List<TenantResponse>>applicationResponse=new ApplicationResponse<>(tenantService.getAllTenants(platformUser));
        return  ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }
    @GetMapping("/{tenantId}")
    public ResponseEntity<ApplicationResponse<TenantResponse>> getTenant(@AuthenticationPrincipal PlatformUsers platformUser,@PathVariable String tenantId){
        ApplicationResponse<TenantResponse>applicationResponse=new ApplicationResponse<>(tenantService.getTenant(tenantId,platformUser));
        return  ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PatchMapping("/{tenantId}")
    public ResponseEntity<ApplicationResponse<UpdateResponse>> updateTenantData( @PathVariable String tenantId, @RequestBody TenantUpdateRequest tenantUpdateRequest){
        ApplicationResponse<UpdateResponse>applicationResponse=new ApplicationResponse<>(tenantService.updateTenantData(tenantId,tenantUpdateRequest));
        return  ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

}
