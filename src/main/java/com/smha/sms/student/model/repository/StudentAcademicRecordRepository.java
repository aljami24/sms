package com.smha.sms.student.model.repository;

import com.smha.sms.student.model.entity.StudentAcademicRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentAcademicRecordRepository extends JpaRepository<StudentAcademicRecord, Long> {
        Optional<StudentAcademicRecord> findByStudentIdAndYearId(Long studentId, Long yearId);
}
