package com.coditas.electricityservicemanagement.tenant.controller;

import com.coditas.electricityservicemanagement.common.dto.request.InvitationRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.InvitationResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.tenant.dto.request.TenantInvitationRequest;
import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import com.coditas.electricityservicemanagement.tenant.service.TenantInvitationService;
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
@RequestMapping("/tenant/invite")
public class TenantInvitationController {

    private final TenantInvitationService tenantInvitationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<InvitationResponse>> sendInvitation(@Valid @RequestBody TenantInvitationRequest invitationRequest, @AuthenticationPrincipal TenantUsers invitedBy){
        ApplicationResponse<InvitationResponse>applicationResponse=new ApplicationResponse<>(tenantInvitationService.sendInvitation(invitationRequest,invitedBy));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

}
