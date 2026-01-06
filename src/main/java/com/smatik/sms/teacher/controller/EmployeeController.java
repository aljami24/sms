package com.smatik.sms.teacher.controller;


import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.teacher.model.dto.request.EmployeeFormDto;
import com.smatik.sms.teacher.model.dto.response.EmployeeResponseDto;
import com.smatik.sms.teacher.model.entity.Employee;
import com.smatik.sms.teacher.model.repository.EmployeeRepository;
import com.smatik.sms.teacher.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/create")
    public String createForm(Model model) {
        Employee employee = new Employee();

        model.addAttribute("employee", employee);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("title", "Create Employee");

        return "employee/employeeForm";
    }

    @GetMapping("/save")
    public String saveEmployee (@Valid @ModelAttribute("EmployeeForm")
                                EmployeeFormDto employeeFormDto,
                                BindingResult bindingResult,
                                Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            return "employee/employeeForm";
        }

        employeeService.saveEmployee(employeeFormDto);
        return "redirect/employee/all";
    }

    @GetMapping("/all")
    public String getAllEmployee(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int pageSize,
                                Model model) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        List<EmployeeResponseDto> employeeResponseDto = employeeService.getAllEmployee(
                page, pageSize, "id", "DESC");

        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize); // The pageSize was added to the model
        model.addAttribute("totalPages", employeePage.getTotalPages());

        model.addAttribute("title", "Teacher List");
        model.addAttribute("Teachers", employeeResponseDto);
        return "teacher/list";
    }
}
