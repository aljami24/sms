package com.smatik.sms.academic.model.repository;

import com.smatik.sms.academic.model.entity.Year;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YearRepository extends JpaRepository <Year, Long> {
}
