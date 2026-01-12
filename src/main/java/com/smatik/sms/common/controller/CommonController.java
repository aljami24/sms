package com.smatik.sms.common.controller;

import com.smatik.sms.employee.model.dto.response.EmployeeResponseDto;
import com.smatik.sms.employee.service.EmployeeService;
import com.smatik.sms.student.model.dto.response.StudentResponseDto;
import com.smatik.sms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
@RequestMapping("/")
public class CommonController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public String home(Model model){
        List<StudentResponseDto> allStudent = studentService.getAllStudent(0, 5, "id", "DESC");
        Page<EmployeeResponseDto> allEmployee = employeeService.getAllEmployee(0, 10, "id", "desc");
        model.addAttribute("allStudent",allStudent);
        model.addAttribute("allEmployee",allEmployee);
        return "dashboard";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
