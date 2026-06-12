package com.coditas.electricityservicemanagement.tenant.controller;

import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.RefreshTokenRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.AccessTokenResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.dto.response.LogoutResponse;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantRegisterRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.TenantRegisterResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import com.coditas.electricityservicemanagement.tenant.service.TenantAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/auth")
public class TenantAuthController {
    private final TenantAuthService tenantAuthService;

    @PostMapping("/register/poc")
    public ResponseEntity<ApplicationResponse<TenantRegisterResponse>> registerTenantPoc(@Valid @RequestBody TenantRegisterRequest request){
        ApplicationResponse<TenantRegisterResponse>applicationResponse=new ApplicationResponse<>(tenantAuthService.registerTenantPoc(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse<TenantRegisterResponse>> registerTenantUser(@Valid @RequestBody TenantRegisterRequest request){
        ApplicationResponse<TenantRegisterResponse>applicationResponse=new ApplicationResponse<>(tenantAuthService.registerTenantUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<LoginResponseTokens>>loginPlatformUser(@Valid @RequestBody LoginRequest request){
        ApplicationResponse<LoginResponseTokens>applicationResponse=new ApplicationResponse<>(tenantAuthService.loginTenantUser(request));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApplicationResponse<LogoutResponse>>logoutUser(@AuthenticationPrincipal TenantUsers tenantUser){
        ApplicationResponse<LogoutResponse>applicationResponse=new ApplicationResponse<>(tenantAuthService.logoutUser(tenantUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse<AccessTokenResponse>>generateAccessToken(@Valid @RequestBody RefreshTokenRequest request, @AuthenticationPrincipal TenantUsers tenantUser){
        ApplicationResponse<AccessTokenResponse>applicationResponse=new ApplicationResponse<>(tenantAuthService.generateAccessToken(request,tenantUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    //for ngrok testing
    @GetMapping("/register")
    public String test(){
        return  "Register Page";
    }

}
