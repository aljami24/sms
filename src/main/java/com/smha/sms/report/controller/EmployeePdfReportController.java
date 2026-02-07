package com.smha.sms.report.controller;

import com.smha.sms.common.constants.Constants;
import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.service.EmployeeService;
import com.smha.sms.report.service.EmployeePdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeePdfReportController {

    private final EmployeePdfReportService employeePdfReportService;

    public EmployeePdfReportController (EmployeePdfReportService employeePdfReportService){
        this.employeePdfReportService = employeePdfReportService;
    }

    @GetMapping("/employee_list_pdf")
    public ResponseEntity<byte[]> teacherListPdf() throws Exception {
        List<Employee> employees = employeePdfReportService.getAllEmployee();

        byte[] pdf = employeePdfReportService.employeeList(
                Constants.EMPLOYEE_PDF_REPORT_PATHS + "employee_list.jrxml",
                "Employee List Report",
                employees
        );
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; fileName=employee.pdf").body(pdf);
    }

}
