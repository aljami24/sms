package com.smatik.sms.attendance.model.repository;

import com.smatik.sms.attendance.model.entity.EmployeeAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAttendanceRepository extends JpaRepository<Long, EmployeeAttendance> {
}
