package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Long> {
    Optional<Invitation> findByEmailAndCode(String email, String code);
}
