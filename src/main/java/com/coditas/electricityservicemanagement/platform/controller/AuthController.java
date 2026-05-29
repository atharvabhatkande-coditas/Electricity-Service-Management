package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.request.LoginRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.LoginResponseTokens;
import com.coditas.electricityservicemanagement.platform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


   /* @PostMapping("/register")
    public ResponseEntity<ApplicationResponse<RegisterResponse>> registerUser(@Valid @RequestBody RegisterRequest request){
        ApplicationResponse<RegisterResponse>applicationResponse=new ApplicationResponse<>(authService.registerUser(request));
        //return new ResponseEntity<>(applicationResponse, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }*/



    @PostMapping("/platform-login")
    public ResponseEntity<ApplicationResponse<LoginResponseTokens>>loginPlatformUser(@Valid @RequestBody LoginRequest request){
        ApplicationResponse<LoginResponseTokens>applicationResponse=new ApplicationResponse<>(authService.loginPlatformUser(request));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }


   /* @PostMapping("/logout")
    public ResponseEntity<ApplicationResponse<LogoutResponse>>logoutUser(){
        ApplicationResponse<LogoutResponse>applicationResponse=new ApplicationResponse<>(authService.logoutUser());
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApplicationResponse<AccessTokenResponse>>generateAccessToken(@Valid @RequestBody RefreshTokenRequest request){
        ApplicationResponse<AccessTokenResponse>applicationResponse=new ApplicationResponse<>(authService.generateAccessToken(request));
        return new ResponseEntity<>(applicationResponse, HttpStatus.OK);
    }*/

    //for ngrok
    @GetMapping("/register")
    public String test(){
        return  "Register Page";
    }
}
