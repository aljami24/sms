package com.smatik.sms.student.model.repository;

import com.smatik.sms.student.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
