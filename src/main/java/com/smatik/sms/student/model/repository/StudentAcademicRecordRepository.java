package com.smatik.sms.student.model.repository;

import com.smatik.sms.student.model.entity.StudentAcademicRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAcademicRecordRepository extends JpaRepository <StudentAcademicRecord, Long> {
}
