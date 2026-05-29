package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, String> {
}
