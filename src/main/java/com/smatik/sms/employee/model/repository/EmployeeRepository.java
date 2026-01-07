package com.smatik.sms.employee.model.repository;

import com.smatik.sms.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
