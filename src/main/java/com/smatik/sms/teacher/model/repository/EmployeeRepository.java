package com.smatik.sms.teacher.model.repository;

import com.smatik.sms.teacher.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
