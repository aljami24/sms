package com.smatik.sms.attendance.model.repository;

import com.smatik.sms.attendance.model.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<Long, StudentAttendance> {
}
