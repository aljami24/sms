package com.smha.sms.attendance.model.repository;

import com.smha.sms.attendance.model.entity.MonthlyHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyHolidayRepository extends JpaRepository<MonthlyHoliday, Long> {
}
