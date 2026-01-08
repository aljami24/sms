package com.smatik.sms.employee.controller;


import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.enums.AddressType;
import com.smatik.sms.common.enums.EmployeeType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import com.smatik.sms.employee.model.dto.request.EmployeeFormDto;
import com.smatik.sms.employee.model.repository.EmployeeRepository;
import com.smatik.sms.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/create")
    public String createForm(Model model) {
        EmployeeFormDto employeeFormDto = new EmployeeFormDto();
        employeeFormDto.getAddressRequestDto().add(new AddressRequestDto());

        model.addAttribute("employeeForm", employeeFormDto);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("employTypes", EmployeeType.values());
        model.addAttribute("identityTypes", IdentityType.values());
        model.addAttribute("addressTypes", AddressType.values());
        model.addAttribute("title", "Create Employee");

        return "employee/employeeForm";
    }

    @PostMapping("/save")
    public String saveEmployee (@Valid @ModelAttribute("employeeForm")
                                EmployeeFormDto employeeFormDto,
                                BindingResult bindingResult,
                                Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("employTypes", EmployeeType.values());
            model.addAttribute("identityTypes", IdentityType.values());
            return "employee/employeeForm";
        }

        employeeService.saveEmployee(employeeFormDto);
        return "redirect:/employee/employeeForm";
    }

//    @GetMapping("/all")
//    public String getAllEmployee(@RequestParam(defaultValue = "0") int page,
//                                @RequestParam(defaultValue = "5") int pageSize,
//                                Model model) {
//
//        Pageable pageable = PageRequest.of(page, pageSize);
//        Page<Employee> employeePage = employeeRepository.findAll(pageable);
//
//        List<EmployeeResponseDto> employeeResponseDto = employeeService.getAllEmployee(
//                page, pageSize, "id", "DESC");
//
//        model.addAttribute("currentPage", page);
//        model.addAttribute("pageSize", pageSize); // The pageSize was added to the model
//        model.addAttribute("totalPages", employeePage.getTotalPages());
//
//        model.addAttribute("title", "Teacher List");
//        model.addAttribute("Teachers", employeeResponseDto);
//        return "teacher/list";
//    }
}
