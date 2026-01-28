package com.smha.sms.common.controller;

import com.smha.sms.employee.model.dto.response.EmployeeResponseDto;
import com.smha.sms.employee.service.EmployeeService;
import com.smha.sms.student.model.dto.response.StudentResponseDto;
import com.smha.sms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CommonController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public String home(Model model) {
        Page<StudentResponseDto> allStudent = studentService.getAllStudent(0, 5, "id", "DESC");
        Page<EmployeeResponseDto> allEmployee = employeeService.getAllEmployee(0, 10, "id", "desc");
        model.addAttribute("allStudent", allStudent);
        model.addAttribute("allEmployee", allEmployee);
        model.addAttribute("breadcrumbCurrent", "Dashboard");
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
