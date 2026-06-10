package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.TenantBilling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantBillingRepository extends JpaRepository<TenantBilling,Long> {
    Page<TenantBilling> findByTenant_Id(String tenantId, Pageable pageable);
}
