package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.Area;
import com.coditas.electricityservicemanagement.platform.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area,Long> {
    Optional<Area> findByName( String areaName);

    Page<Area> findByCity_Id(Long cityId, Pageable pageable);

    Page<Area> findByCityIn(List<City> cities,Pageable pageable);
}
