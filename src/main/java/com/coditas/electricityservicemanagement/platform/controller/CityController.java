package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.CityHeadAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.CityRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> addCity(@Valid @RequestBody CityRequest cityRequest, @AuthenticationPrincipal PlatformUsers districtHead){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(cityService.addCity(cityRequest,districtHead));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<StateDistrictCityResponse>>>getAllCitiesOfALlStates(@PathVariable int pageNo){
        ApplicationResponse<List<StateDistrictCityResponse>>applicationResponse=new ApplicationResponse<>(cityService.getALlCityOfAllStates(pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/state/{stateId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<StateDistrictCityResponse>>getAllCityOfState(@PathVariable Long stateId ,@PathVariable int pageNo,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<StateDistrictCityResponse>applicationResponse=new ApplicationResponse<>(cityService.getALlCityOfState(stateId,pageNo,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }


    @GetMapping("/district/{districtId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<DistrictCityResponse>>getCitiesOfDistrict(@PathVariable Long districtId, @AuthenticationPrincipal PlatformUsers platformUser, @PathVariable int pageNo){
        ApplicationResponse<DistrictCityResponse>applicationResponse=new ApplicationResponse<>(cityService.getCitiesOfDistrict(districtId,pageNo,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<ApplicationResponse<CityResponse>>getCity(@PathVariable Long cityId, @AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<CityResponse>applicationResponse=new ApplicationResponse<>(cityService.getCity(cityId,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @PatchMapping("/{cityId}/assign/city-head")
    public ResponseEntity<ApplicationResponse<SingleResponse>>assignCityHead(@PathVariable Long cityId , @Valid @RequestBody CityHeadAssignRequest cityHeadAssignRequest, @AuthenticationPrincipal PlatformUsers districtHead){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(cityService.assignCityHead(cityHeadAssignRequest,cityId,districtHead));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }



}
