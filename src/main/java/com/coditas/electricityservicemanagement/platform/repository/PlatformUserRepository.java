package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUsers ,Long> {
    Optional<PlatformUsers> findByUsername(String username);
}
