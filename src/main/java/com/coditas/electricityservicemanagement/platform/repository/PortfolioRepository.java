package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.dto.response.PortfolioResponse;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.Portfolio;
import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
    Optional<Portfolio> findByTenantAndSalesUserId(Tenant tenant, PlatformUsers salesPerson);

    Page<Portfolio> findBySalesUserId(PlatformUsers platformUser, Pageable pageable);
}
