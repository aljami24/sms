package com.smha.sms.systemConfiguration.fee.model.repository;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findAllByClassroomVersionSectionIdAndYearIdId(Long cvsId, Long yearId);

    @Query("""
       SELECT DISTINCT f.feeType
       FROM Fee f
       WHERE f.yearId.id = :yearId
       """)
    List<FeeType> findFeeTypesByYear(@Param("yearId") Long yearId);
}
