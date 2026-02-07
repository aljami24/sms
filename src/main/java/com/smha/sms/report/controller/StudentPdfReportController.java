package com.smha.sms.report.controller;

import com.smha.sms.common.constants.Constants;
import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.service.EmployeeService;
import com.smha.sms.report.service.EmployeePdfReportService;
import com.smha.sms.report.service.StudentPdfReportService;
import com.smha.sms.student.model.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentPdfReportController {

    @Autowired
    StudentPdfReportService studentPdfReportService;

}
