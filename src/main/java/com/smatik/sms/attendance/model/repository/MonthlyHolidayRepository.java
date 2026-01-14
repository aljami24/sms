package com.smatik.sms.attendance.model.repository;

import com.smatik.sms.attendance.model.entity.MonthlyHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyHolidayRepository extends JpaRepository<Long, MonthlyHoliday> {
}
