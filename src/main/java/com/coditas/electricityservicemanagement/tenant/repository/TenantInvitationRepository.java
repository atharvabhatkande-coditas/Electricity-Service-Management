package com.coditas.electricityservicemanagement.tenant.repository;
import com.coditas.electricityservicemanagement.tenant.entity.TenantInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantInvitationRepository extends JpaRepository<TenantInvitation,Long> {
    Optional<TenantInvitation> findByEmailAndCode(String username, String code);
}
