package com.smha.sms.employee.controller;


import com.smha.sms.annotation.PermissionRequired;
import com.smha.sms.common.address.dto.AddressRequestDto;
import com.smha.sms.common.address.entity.Address;
import com.smha.sms.common.address.repository.DistrictRepository;
import com.smha.sms.common.address.repository.DivisionRepository;
import com.smha.sms.common.address.repository.PoliceStationRepository;
import com.smha.sms.common.enums.AddressType;
import com.smha.sms.common.enums.EmployeeType;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import com.smha.sms.employee.model.dto.EmployeeFilter;
import com.smha.sms.employee.model.dto.request.EmployeeFormDto;
import com.smha.sms.employee.model.dto.response.EmployeeResponseDto;
import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.model.enums.EmployeeStatus;
import com.smha.sms.employee.model.repository.EmployeeRepository;
import com.smha.sms.employee.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    PoliceStationRepository policeStationRepository;

    //Save Form View---------------------------------------------------------------------------------
    @PermissionRequired("EMPLOYEE_CREATE")
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

    //Save Employee---------------------------------------------------------------------------------
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employeeForm")
                               EmployeeFormDto employeeFormDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("employTypes", EmployeeType.values());
            model.addAttribute("identityTypes", IdentityType.values());
            return "employee/employeeForm";
        }

        employeeService.saveEmployee(employeeFormDto);
        return "redirect:/employee/list";
    }

    //Update Form View---------------------------------------------------------------------------------
    @PermissionRequired("EMPLOYEE_UPDATE")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int pageSize,
                           Model model) {
        Employee employee = employeeService.editById(id).orElseThrow();

        EmployeeFormDto employeeFormDto = new EmployeeFormDto();

        employeeFormDto.setId(employee.getId());
        employeeFormDto.setEmployeeId(employee.getEmployeeId());
        employeeFormDto.setName(employee.getName());
        employeeFormDto.setDob(employee.getDob());
        employeeFormDto.setJoiningDate(employee.getJoiningDate());
        employeeFormDto.setSalary(employee.getSalary());
        employeeFormDto.setGender(employee.getGender());
        employeeFormDto.setEmployeeType(employee.getEmployeeType());
        employeeFormDto.setIdentityType(employee.getIdentityType());
        employeeFormDto.setIdentityNumber(employee.getIdentityNumber());
        employeeFormDto.setPhoneNumber(employee.getPhoneNumber());
        employeeFormDto.setPhotoDir(employee.getPhotoDir());
        employeeFormDto.setNidDir(employee.getNidDir());

        List<AddressRequestDto> addressDtoList = new ArrayList<>();

        if (employee.getAddress() != null) {
            for (Address address : employee.getAddress()) {
                AddressRequestDto dto = new AddressRequestDto();
                dto.setAddressType(address.getAddressType());
                dto.setDivision(address.getDivision());
                dto.setDistrict(address.getDistrict());
                dto.setPoliceStation(address.getPoliceStation());
                dto.setVillage(address.getVillage());

                addressDtoList.add(dto);
            }
        }

        employeeFormDto.setAddressRequestDto(addressDtoList);


        model.addAttribute("divisions", divisionRepository.findAll());
        model.addAttribute("districts", districtRepository.findAll());
        model.addAttribute("policeStations", policeStationRepository.findAll());
        model.addAttribute("employeeForm", employeeFormDto);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("employTypes", EmployeeType.values());
        model.addAttribute("identityTypes", IdentityType.values());
        model.addAttribute("addressTypes", AddressType.values());
        model.addAttribute("title", "Edit Teacher");

        // The current page number and size are passed to the form via the model (for hidden fields)
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);

        return "employee/employeeForm";
    }


    //Update Employee---------------------------------------------------------------------------------
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute("employeeForm") EmployeeFormDto employeeFormDto,
                                 BindingResult bindingResult, Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int pageSize) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("employTypes", EmployeeType.values());
            model.addAttribute("identityTypes", IdentityType.values());
            model.addAttribute("addressTypes", AddressType.values());
            model.addAttribute("title", "Update Employee");
            // If there are errors, the page and pageSize are added back to the form
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", pageSize);
            return "/employee/employeeForm";
        }
        employeeService.updateExistingEmployee(employeeFormDto);
        // Redirected after the update using the current page number and size as parameters
        return "redirect:/employee/list?page=" + page + "&pageSize=" + pageSize + "#employee-" + id;
    }

    // Employee List---------------------------------------------------------------------------------
    @PermissionRequired("EMPLOYEE_LIST")
    @GetMapping("/list")
    public String getAllEmployee(@ModelAttribute("filter")EmployeeFilter filter,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int pageSize,
                                 @RequestParam(defaultValue = "id") String sortField,
                                 @RequestParam(defaultValue = "desc") String sortOrder,
                                 Model model) {


        Page<EmployeeResponseDto> employeePage = employeeService.getAllFilterEmployee(
                filter, page, pageSize, sortField, sortOrder);

        model.addAttribute("employees", employeePage);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("employeeTypes", EmployeeType.values());
        model.addAttribute("allstatus", EmployeeStatus.values());
        model.addAttribute("divisions", divisionRepository.findAll());
        model.addAttribute("districts", districtRepository.findAll());
        model.addAttribute("police_station", policeStationRepository.findAll());
        model.addAttribute("title", "Employee List");
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("totalPages", employeePage.getTotalPages());

        return "employee/employeeList";
    }

    // ================= View Employee =================
    @PermissionRequired("EMPLOYEE_VIEW")
    @GetMapping("/view/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {

        EmployeeResponseDto employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);
        model.addAttribute("title", "Employee Details");

        return "employee/employeeDetail"; // Thymeleaf template
    }


    // =================  Delete =================
    @PermissionRequired("EMPLOYEE_DELETE")
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee/list";
    }

    // ================= Join Employee =================
    @PermissionRequired("EMPLOYEE_ACTIVE")
    @PostMapping("/activate/{id}")
    public String activateEmployee(@PathVariable Long id) {
        employeeService.activateEmployee(id);
        return "redirect:/employee/list"; // Redirect back to list
    }

    // ================= Deactivate Employee =================
    @PermissionRequired("EMPLOYEE_DEACTIVE")
    @PostMapping("/deactivate/{id}")
    public String deactivateEmployee(@PathVariable Long id) {
        employeeService.deactivateEmployee(id);
        return "redirect:/employee/list"; // Redirect back to list
    }

}



