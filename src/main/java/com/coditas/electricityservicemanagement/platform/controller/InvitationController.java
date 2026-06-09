package com.coditas.electricityservicemanagement.platform.controller;


import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.InvitationResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/invite")
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/poc")
    public ResponseEntity<ApplicationResponse<InvitationResponse>> sendInvitationToTenantPoc(@Valid @RequestBody InvitationRequest invitationRequest, @AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<InvitationResponse>applicationResponse=new ApplicationResponse<>(invitationService.sendInvitationToTenantPoc(invitationRequest,platformUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

}
