package com.smha.sms.systemConfiguration.fee.model.repository;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findAllByClassroomVersionSectionIdAndYearIdId(Long cvsId, Long yearId);
}
