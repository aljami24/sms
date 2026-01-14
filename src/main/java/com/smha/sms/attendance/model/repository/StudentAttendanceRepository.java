package com.smha.sms.attendance.model.repository;

import com.smha.sms.attendance.model.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
}
