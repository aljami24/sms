package com.smatik.sms.common.address.repository;

import com.smatik.sms.common.address.entity.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoliceStationRepository extends JpaRepository<PoliceStation, Long> {
    List<PoliceStation> findByDistrictIdAndActiveTrueOrderByNameAsc(Long districtId);
}
