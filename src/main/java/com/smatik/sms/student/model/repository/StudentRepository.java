package com.smatik.sms.student.model.repository;

import com.smatik.sms.student.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRoll(Integer roll);

    Optional<Student> findByRoll(int roll);


}