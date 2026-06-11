package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.Area;
import com.coditas.electricityservicemanagement.platform.entity.ServiceArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceAreaRepository extends JpaRepository<ServiceArea,Long> {
    Optional<ServiceArea> findByArea_Id(Long areaId);

    Page<ServiceArea> findByAreaIn(List<Area> content, Pageable pageable);
}
