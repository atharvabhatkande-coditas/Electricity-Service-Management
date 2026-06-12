package com.coditas.electricityservicemanagement.tenant.repository;

import com.coditas.electricityservicemanagement.tenant.entity.MeterBillingPlan;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeterBillingPlanRepository extends JpaRepository<MeterBillingPlan,Long> {
    Optional<MeterBillingPlan> findByMeterType_Id(Long meterTypeId);
}
