package com.smha.sms.common.address.repository;

import com.smha.sms.common.address.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findByDivisionIdAndActiveTrueOrderByNameAsc(Long divisionId);
}
