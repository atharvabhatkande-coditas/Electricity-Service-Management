package com.coditas.electricityservicemanagement.platform.service;

import com.coditas.electricityservicemanagement.common.dto.response.SingleResponse;
import com.coditas.electricityservicemanagement.common.exception.AlreadyExistException;
import com.coditas.electricityservicemanagement.common.exception.AuthenticationException;
import com.coditas.electricityservicemanagement.common.exception.NotFoundException;
import com.coditas.electricityservicemanagement.platform.dto.request.PortfolioAssignRequest;
import com.coditas.electricityservicemanagement.platform.dto.request.PortfolioUpdateRequest;
import com.coditas.electricityservicemanagement.platform.dto.response.ErrorResponse;
import com.coditas.electricityservicemanagement.platform.dto.response.PortfolioResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.Portfolio;
import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import com.coditas.electricityservicemanagement.platform.enums.RoleType;
import com.coditas.electricityservicemanagement.platform.mappers.PortfolioMapper;
import com.coditas.electricityservicemanagement.platform.repository.PlatformUserRepository;
import com.coditas.electricityservicemanagement.platform.repository.PortfolioRepository;
import com.coditas.electricityservicemanagement.platform.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.coditas.electricityservicemanagement.common.constants.ValidationConstants.NOT_FOUND;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.UNAUTHORIZED;
import static com.coditas.electricityservicemanagement.platform.constants.AuthConstants.USER_NOT_FOUND;
import static com.coditas.electricityservicemanagement.platform.constants.TenantConstants.*;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PlatformUserRepository platformUserRepository;
    private final TenantRepository tenantRepository;
    private final PortfolioMapper portfolioMapper;


    @Transactional
    public SingleResponse assignPortfolio(PortfolioAssignRequest portfolioAssignRequest,PlatformUsers assignedBy) {
        PlatformUsers salesPerson=platformUserRepository.findByUsername(portfolioAssignRequest.getSalesPersonEmail())
                .orElseThrow(()->new AuthenticationException(USER_NOT_FOUND));

        Tenant tenant=tenantRepository.findById(portfolioAssignRequest.getTenantId())
                .orElseThrow(()->new NotFoundException(TENANT+NOT_FOUND));
        Portfolio portfolio=portfolioRepository.findByTenantAndSalesUserId(tenant,salesPerson)
                .orElse(null);
        
        if(!Objects.isNull(portfolio)){
            throw new AlreadyExistException(PORTFOLIO_ALREADY_ASSIGNED);
        }
        
        Portfolio newPortfolio=Portfolio.builder()
                .tenant(tenant)
                .salesUserId(salesPerson)
                .assignedBy(assignedBy)
                .isActive(true)
                .build();

        portfolioRepository.save(newPortfolio);
        
        return SingleResponse.builder()
                .message(PORTFOLIO_ASSIGNED)
                .build();
    }

    public List<PortfolioResponse> getAllPortfolios(int pageNo,PlatformUsers platformUser) {
        Pageable pageable= PageRequest.of(pageNo,5);
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.SALES){
            return portfolioRepository.findBySalesUserId(platformUser,pageable)
                    .stream()
                    .map(portfolioMapper::portfolioResponse)
                    .toList();
        }else{
            return portfolioRepository.findAll(pageable)
                    .stream()
                    .map(portfolioMapper::portfolioResponse)
                    .toList();
        }
    }

    public PortfolioResponse getPortfolio(Long portfolioId, PlatformUsers platformUser) {

        Portfolio portfolio=portfolioRepository.findById(portfolioId)
                .orElseThrow(()->new NotFoundException(PORTFOLIO+NOT_FOUND));
        RoleType higherRole=RoleType.getHighestPriorityRole(platformUser.getRoles());
        if(higherRole==RoleType.SALES){
            if(!Objects.equals(portfolio.getSalesUserId().getUsername(),platformUser.getUsername())){
                throw new AuthenticationException(UNAUTHORIZED);
            }
        }
        return portfolioMapper.portfolioResponse(portfolio);
    }
    @Transactional
    public SingleResponse updatePortfolio(Long portfolioId, PortfolioUpdateRequest portfolioUpdateRequest) {
        Portfolio portfolio=portfolioRepository.findById(portfolioId)
                .orElseThrow(()->new NotFoundException(PORTFOLIO+NOT_FOUND));

        if(!Objects.isNull(portfolioUpdateRequest.getTenantId())){
            Tenant tenant=tenantRepository.findById(portfolioUpdateRequest.getTenantId())
                    .orElseThrow(()->new NotFoundException(TENANT+NOT_FOUND));
            portfolio.setTenant(tenant);
        }

        if(!Objects.isNull(portfolioUpdateRequest.getSalesPersonEmail())){
            PlatformUsers salesPerson=platformUserRepository.findByUsername(portfolioUpdateRequest.getSalesPersonEmail())
                    .orElseThrow(()->new NotFoundException(USER_NOT_FOUND));
            portfolio.setSalesUserId(salesPerson);
        }

        if(!Objects.isNull(portfolioUpdateRequest.getTenantId())){
            portfolio.setActive(portfolioUpdateRequest.getIsActive());
        }

            portfolioRepository.save(portfolio);

        return SingleResponse.builder()
                .message(PORTFOLIO_UPDATED)
                .build();
    }
}
