package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.DistrictHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.DistrictRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.DistrictResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.DistrictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/district")
public class DistrictController {

    private final DistrictService districtService;
    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> addDistrict(@Valid @RequestBody DistrictRequest districtRequest, @AuthenticationPrincipal PlatformUsers stateHead){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(districtService.addDistrict(districtRequest,stateHead));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<DistrictResponse>>>getAllDistricts(@PathVariable int pageNo){
        ApplicationResponse<List<DistrictResponse>>applicationResponse=new ApplicationResponse<>(districtService.getALlDistricts(pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/state/{stateId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<DistrictResponse>>>getAllDistrictsOfState(@PathVariable Long stateId ,@PathVariable int pageNo,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<List<DistrictResponse>>applicationResponse=new ApplicationResponse<>(districtService.getALlDistrictsOfState(stateId,pageNo,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/{districtId}")
    public ResponseEntity<ApplicationResponse<DistrictResponse>>getDistrict(@PathVariable Long districtId,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<DistrictResponse>applicationResponse=new ApplicationResponse<>(districtService.getDistrict(districtId,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PatchMapping("/{districtId}/assign/district-head")
    public ResponseEntity<ApplicationResponse<SingleResponse>>assignDistrictHead(@PathVariable Long districtId , @Valid @RequestBody DistrictHeadAssignRequest districtHeadAssignRequest, @AuthenticationPrincipal PlatformUsers stateHead){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(districtService.assignDistrictHead(districtHeadAssignRequest,districtId,stateHead));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

}
