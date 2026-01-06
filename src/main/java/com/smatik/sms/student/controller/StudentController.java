package com.smatik.sms.student.controller;

import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.address.entity.District;
import com.smatik.sms.common.address.entity.Division;
import com.smatik.sms.common.address.entity.PoliceStation;
import com.smatik.sms.common.address.repository.DistrictRepository;
import com.smatik.sms.common.address.repository.DivisionRepository;
import com.smatik.sms.common.address.repository.PoliceStationRepository;
import com.smatik.sms.common.enums.AddressType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private DivisionRepository divisionRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private PoliceStationRepository policeStationRepository;
    @Autowired
    private StudentService studentService;

    //  show form

    @GetMapping("/create")
    public String createForm(Model model) {
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        studentRequestDto.getAddresses().add(new AddressRequestDto());
        model.addAttribute("studentForm", studentRequestDto);
        model.addAttribute("addressTypes", AddressType.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("identityTypes", IdentityType.values());
        model.addAttribute("title", "Student Form");
        return "student/studentForm";
    }

    @GetMapping("/divisions")
    @ResponseBody
    public List<Division> getAllDivisions() {
        return divisionRepository.findByActiveTrueOrderByNameAsc();
    }

    @GetMapping("/districts")
    @ResponseBody
    public List<District> getDistricts(@RequestParam Long divisionId) {
        return districtRepository.findByDivisionIdAndActiveTrueOrderByNameAsc(divisionId);
    }

    @GetMapping("/police-stations")
    @ResponseBody
    public List<PoliceStation> getPoliceStations(@RequestParam Long districtId) {
        return policeStationRepository.findByDistrictIdAndActiveTrueOrderByNameAsc(districtId);
    }

    @PostMapping("/save")
    public String saveStudent(StudentRequestDto studentRequestDto) {
        studentService.saveStudent(studentRequestDto);
        return "redirect:/student/create";
    }
}
