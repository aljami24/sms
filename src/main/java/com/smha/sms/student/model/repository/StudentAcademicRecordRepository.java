package com.smha.sms.student.model.repository;

import com.smha.sms.student.model.entity.StudentAcademicRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAcademicRecordRepository extends JpaRepository<StudentAcademicRecord, Long> {
}
