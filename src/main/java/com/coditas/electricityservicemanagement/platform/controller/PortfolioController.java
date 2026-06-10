package com.coditas.electricityservicemanagement.platform.controller;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.platform.dto.request.PortfolioAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.PortfolioUpdateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ApplicationResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.PortfolioResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<ApplicationResponse<SingleResponse>>assignPortfolio(@Valid @RequestBody PortfolioAssignRequest portfolioAssignRequest, @AuthenticationPrincipal PlatformUsers assignedBy){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(portfolioService.assignPortfolio(portfolioAssignRequest,assignedBy));
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationResponse);

    }

    @GetMapping("/page/{pageNo}")
    public ResponseEntity<ApplicationResponse<List<PortfolioResponse>>>getAllPortfolios(@PathVariable int pageNo,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<List<PortfolioResponse>>applicationResponse=new ApplicationResponse<>(portfolioService.getAllPortfolios(pageNo,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);

    }
    @GetMapping("/{portfolioId}")
    public ResponseEntity<ApplicationResponse<PortfolioResponse>>getPortfolio(@PathVariable Long portfolioId,@AuthenticationPrincipal PlatformUsers platformUser){
        ApplicationResponse<PortfolioResponse>applicationResponse=new ApplicationResponse<>(portfolioService.getPortfolio(portfolioId,platformUser));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);

    }

    @PatchMapping("/{portfolioId}")
    public ResponseEntity<ApplicationResponse<SingleResponse>>updatePortfolio(@PathVariable Long portfolioId,@Valid @RequestBody PortfolioUpdateRequest portfolioUpdateRequest){
        ApplicationResponse<SingleResponse>applicationResponse=new ApplicationResponse<>(portfolioService.updatePortfolio(portfolioId,portfolioUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).body(applicationResponse);

    }

}
