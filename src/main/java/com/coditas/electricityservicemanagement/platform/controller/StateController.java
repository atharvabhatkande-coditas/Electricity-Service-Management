package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.StateHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.StateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.StateResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/state")
public class StateController {
    private final StateService stateService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>addState(@Valid @RequestBody StateRequest stateRequest, @AuthenticationPrincipal PlatformUsers invitedBy){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(stateService.addState(stateRequest,invitedBy));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<StateResponse>>>getAllStates(@PathVariable int pageNo){
        ApplicationResponse<List<StateResponse>>applicationResponse=new ApplicationResponse<>(stateService.getALlStates(pageNo));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/{stateId}")
    public ResponseEntity<ApplicationResponse<StateResponse>>getState(@PathVariable Long stateId,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<StateResponse>applicationResponse=new ApplicationResponse<>(stateService.getState(stateId,platformUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PatchMapping("/{stateId}/assign/state-head")
    public ResponseEntity<ApplicationResponse<SingleResponse>>assignStateHead(@PathVariable Long stateId , @Valid @RequestBody StateHeadAssignRequest stateHeadAssignRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(stateService.assignStateHead(stateHeadAssignRequest,stateId));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }


}
