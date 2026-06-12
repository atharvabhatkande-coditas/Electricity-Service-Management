package com.coditas.electricityservicemanagement.tenant.repository;

import com.coditas.electricityservicemanagement.tenant.entity.MeterType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeterTypeRepository extends JpaRepository<MeterType,Long> {
    Optional<MeterType> findByName(String meterName);
}
