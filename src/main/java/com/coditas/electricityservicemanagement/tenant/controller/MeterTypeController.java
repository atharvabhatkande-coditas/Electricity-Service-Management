package com.coditas.electricityservicemanagement.tenant.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterTypeRequest;
import com.coditas.electricityservicemanagement.tenant.dto.request.MeterUpdateRequest;
import com.coditas.electricityservicemanagement.tenant.dto.response.MeterTypeResponse;
import com.coditas.electricityservicemanagement.tenant.service.MeterTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/meter-type")
public class MeterTypeController {

    private final MeterTypeService meterTypeService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>addMeterType(@RequestBody MeterTypeRequest meterTypeRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(meterTypeService.addMeterType(meterTypeRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);
    }

    @PatchMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>updateMeterType(@RequestBody MeterUpdateRequest meterUpdateRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(meterTypeService.updateMeterType(meterUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }
    @GetMapping("/{meterTypeId}")
    public ResponseEntity<ApplicationResponse<MeterTypeResponse>>getMeterType(@PathVariable Long meterTypeId){
        ApplicationResponse<MeterTypeResponse>applicationResponse=new ApplicationResponse<>(meterTypeService.getMeterType(meterTypeId));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<MeterTypeResponse>>>getAllMeterType(@PathVariable int pageNo){
        ApplicationResponse<List<MeterTypeResponse>>applicationResponse=new ApplicationResponse<>(meterTypeService.getAllMeterType(pageNo));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);
    }


}
