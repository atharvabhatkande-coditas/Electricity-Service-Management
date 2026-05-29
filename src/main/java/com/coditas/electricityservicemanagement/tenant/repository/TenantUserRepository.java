package com.coditas.electricityservicemanagement.tenant.repository;

import com.coditas.electricityservicemanagement.tenant.entity.TenantUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantUserRepository extends JpaRepository<TenantUsers ,Long> {
    Optional<TenantUsers> findByUsername(String username);
}
