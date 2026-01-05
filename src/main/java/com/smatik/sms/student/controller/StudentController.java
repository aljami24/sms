package com.smatik.sms.student.controller;

import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.enums.AddressType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * StudentController
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    //    show form
    @GetMapping("/create")
    public String createForm(Model model) {
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        studentRequestDto.getAddresses().add(new AddressRequestDto());
        model.addAttribute("studentForm", studentRequestDto);
        model.addAttribute("addressType", AddressType.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("identityType", IdentityType.values());
        return "student/studentForm";
    }
}
