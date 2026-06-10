package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.TenantRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRefreshTokenRepository extends JpaRepository<TenantRefreshToken,Long> {
    Optional<TenantRefreshToken> findByUsername(String username);
}
