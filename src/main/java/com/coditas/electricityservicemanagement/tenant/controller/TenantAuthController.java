package com.coditas.electricityservicemanagement.tenant.controller;

import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantRegisterRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.TenantRegisterResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.tenant.service.TenantAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TenantAuthController {
    private final TenantAuthService tenantAuthService;

    @PostMapping("/tenant-register")
    public ResponseEntity<ApplicationResponse<TenantRegisterResponse>> registerUser(@Valid @RequestBody TenantRegisterRequest request){
        ApplicationResponse<TenantRegisterResponse>applicationResponse=new ApplicationResponse<>(tenantAuthService.registerTenantUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PostMapping("/tenant-login")
    public ResponseEntity<ApplicationResponse<LoginResponseTokens>>loginPlatformUser(@Valid @RequestBody LoginRequest request){
        ApplicationResponse<LoginResponseTokens>applicationResponse=new ApplicationResponse<>(tenantAuthService.loginTenantUser(request));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

}
