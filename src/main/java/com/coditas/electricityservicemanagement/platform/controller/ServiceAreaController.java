package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.AssignTechnicianRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.ServiceAreaAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.ServiceAreaRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.ServiceAreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/service-area")
public class ServiceAreaController {
    private final ServiceAreaService serviceAreaService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> addServiceArea(@Valid @RequestBody ServiceAreaRequest serviceAreaRequest, @AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(serviceAreaService.addServiceArea(serviceAreaRequest,platformUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PatchMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> assignTechnicianOrBiller(@Valid @RequestBody ServiceAreaAssignRequest serviceAreaAssignRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(serviceAreaService.assignTechnicianOrBiller(serviceAreaAssignRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/{serviceAreaId}")
    public ResponseEntity<ApplicationResponse<ServiceAreaResponse>> getServiceArea(@PathVariable Long serviceAreaId,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<ServiceAreaResponse>applicationResponse=new ApplicationResponse<>(serviceAreaService.getServiceArea(serviceAreaId,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/city/{cityId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<CityServiceAreaResponse>> getServiceAreaOfCity(@PathVariable Long cityId,@AuthenticationPrincipal PlatformUsers platformUser,@PathVariable int pageNo){
        ApplicationResponse<CityServiceAreaResponse>applicationResponse=new ApplicationResponse<>(serviceAreaService.getServiceAreaOfCity(cityId,platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/district/{districtId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<DistrictCityServiceAreaResponse>> getServiceAreaOfDistrict(@PathVariable Long districtId, @AuthenticationPrincipal PlatformUsers platformUser, @PathVariable int pageNo){
        ApplicationResponse<DistrictCityServiceAreaResponse>applicationResponse=new ApplicationResponse<>(serviceAreaService.getServiceAreaOfDistrict(districtId,platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/state/{stateId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<StateDistrictCityServiceAreaResponse>> getServiceAreaOfState(@PathVariable Long stateId, @AuthenticationPrincipal PlatformUsers platformUser, @PathVariable int pageNo){
        ApplicationResponse<StateDistrictCityServiceAreaResponse>applicationResponse=new ApplicationResponse<>(serviceAreaService.getServiceAreaOfState(stateId,platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }


}
