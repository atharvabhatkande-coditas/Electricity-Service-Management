package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import com.coditas.electricityservicemanagement.platform.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District,Long> {
    Optional<District> findByName(String districtName);

    Optional<District> findByDistrictHead(PlatformUsers districtHead);

    Page<District> findByState_Id(Long stateId, Pageable pageable);

    Page<District> findByStateIn(List<State> states, Pageable pageable);
}
