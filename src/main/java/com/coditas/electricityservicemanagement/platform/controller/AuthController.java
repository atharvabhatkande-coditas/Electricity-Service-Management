package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.RefreshTokenRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.RegisterRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApplicationResponse<RegisterResponse>>registerNewPlatformUser(@Valid @RequestBody RegisterRequest registerRequest){
        ApplicationResponse<RegisterResponse>applicationResponse=new ApplicationResponse<>(authService.registerPlatformUser(registerRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationResponse<LoginResponseTokens>>loginPlatformUser(@Valid @RequestBody LoginRequest request){
        ApplicationResponse<LoginResponseTokens>applicationResponse=new ApplicationResponse<>(authService.loginPlatformUser(request));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<ApplicationResponse<LogoutResponse>>logoutUser(@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<LogoutResponse>applicationResponse=new ApplicationResponse<>(authService.logoutUser(platformUser));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse<AccessTokenResponse>>generateAccessToken(@Valid @RequestBody RefreshTokenRequest request,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<AccessTokenResponse>applicationResponse=new ApplicationResponse<>(authService.generateAccessToken(request,platformUser));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    //for ngrok testing
    @GetMapping("/register")
    public String test(){
        return  "Register Page";
    }
}
