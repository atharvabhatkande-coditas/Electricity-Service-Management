package com.coditas.electricityservicemanagement.platform.repository;

import com.coditas.electricityservicemanagement.platform.entity.City;
import com.coditas.electricityservicemanagement.platform.entity.District;
import com.coditas.electricityservicemanagement.platform.entity.PlatformUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {
    Optional<City> findByName( String cityName);

    Optional<City> findByCityHead(PlatformUsers platformUser);

    Page<City> findByDistrictIn(List<District> districtList, Pageable pageable);

    Page<City> findByDistrict_Id(Long districtId,Pageable pageable);
}
