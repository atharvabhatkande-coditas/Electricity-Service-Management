package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.AreaRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.*;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/area")
public class AreaController {
    private final AreaService areaService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>> addArea(@Valid @RequestBody AreaRequest areaRequest, @AuthenticationPrincipal PlatformUsers cityHead){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(areaService.addArea(areaRequest,cityHead));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<ApplicationResponse<AreaResponse>>getArea(@PathVariable Long areaId, @AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<AreaResponse>applicationResponse=new ApplicationResponse<>(areaService.getArea(areaId,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/city/{cityId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<CityAreaResponse>>getAreaOfCity(@PathVariable Long cityId, @AuthenticationPrincipal PlatformUsers platformUser,@PathVariable int pageNo){
        ApplicationResponse<CityAreaResponse>applicationResponse=new ApplicationResponse<>(areaService.getAreaOfCity(cityId,platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/district/{districtId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<DistrictCityAreaResponse>>getAreaOfDistrict(@PathVariable Long districtId, @AuthenticationPrincipal PlatformUsers platformUser,@PathVariable int pageNo){
        ApplicationResponse<DistrictCityAreaResponse>applicationResponse=new ApplicationResponse<>(areaService.getAreaOfDistrict(districtId,platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/state/{stateId}/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<StateDistrictCityAreaResponse>>getAreaOfState(@PathVariable Long stateId, @AuthenticationPrincipal PlatformUsers platformUser,@PathVariable int pageNo){
        ApplicationResponse<StateDistrictCityAreaResponse>applicationResponse=new ApplicationResponse<>(areaService.getAreaOfState(stateId,platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }
    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<StateDistrictCityAreaResponse>>>getAreaOfAllState(@AuthenticationPrincipal PlatformUsers platformUser, @PathVariable int pageNo){
        ApplicationResponse<List<StateDistrictCityAreaResponse>>applicationResponse=new ApplicationResponse<>(areaService.getAreaOfAllState(platformUser,pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }








}
