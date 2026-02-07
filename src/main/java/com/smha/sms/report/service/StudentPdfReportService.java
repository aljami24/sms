package com.smha.sms.report.service;

import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.model.repository.EmployeeRepository;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentPdfReportService {



  @Autowired
  StudentRepository studentRepository;


}
