package com.smha.sms.employee.model.repository;

import com.smha.sms.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
