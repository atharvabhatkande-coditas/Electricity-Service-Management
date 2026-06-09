package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PocInvitationRepository extends JpaRepository<Invitation,Long> {
}
