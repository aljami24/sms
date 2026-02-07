package com.smha.sms.report.service;

import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.model.repository.EmployeeRepository;
import com.smha.sms.report.PdfGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeePdfReportService {

  @Autowired
  PdfGenerate pdfGenerate;

  @Autowired
    EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public byte[] employeeList(String jrxmlPath, String title, List<?> data) throws Exception {

        Map<String,Object> param = new HashMap<>();
        param.put("TITLE",title);
        param.put("REPORT_DATE", new Date());
        return pdfGenerate.generatePdf(jrxmlPath,param,data);
    }
}
