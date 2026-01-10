package com.smatik.sms.student.controller;

import com.smatik.sms.academic.model.repository.ClassRoomRepository;
import com.smatik.sms.academic.model.repository.SectionRepository;
import com.smatik.sms.academic.model.repository.VersionRepository;
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
import com.smatik.sms.student.model.dto.response.StudentResponseDto;
import com.smatik.sms.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * StudentController
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final ClassRoomRepository classRoomRepository;
    private final VersionRepository versionRepository;
    private final SectionRepository sectionRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final PoliceStationRepository policeStationRepository;
    private final StudentService studentService;

    //  Student Form Show
    @GetMapping("/create")
    public String createForm(Model model) {
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        studentRequestDto.getAddresses().add(new AddressRequestDto());
        model.addAttribute("studentForm", studentRequestDto);
        model.addAttribute("addressTypes", AddressType.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("identityTypes", IdentityType.values());
        model.addAttribute("title", "Student Form");
        model.addAttribute("classRoom", classRoomRepository.findAll());
        model.addAttribute("version", versionRepository.findAll());
        model.addAttribute("section", sectionRepository.findAll());
        return "student/studentForm";
    }

    //   Division Load For Address
    @GetMapping("/divisions")
    @ResponseBody
    public List<Division> getAllDivisions() {
        return divisionRepository.findByActiveTrueOrderByNameAsc();
    }

    //   District Load For Address
    @GetMapping("/districts")
    @ResponseBody
    public List<District> getDistricts(@RequestParam("divisionId") Long divisionId) {
        return districtRepository.findByDivisionIdAndActiveTrueOrderByNameAsc(divisionId);
    }

    //   Police_Station Load For Address
    @GetMapping("/police-stations")
    @ResponseBody
    public List<PoliceStation> getPoliceStations(@RequestParam("districtId") Long districtId) {
        return policeStationRepository.findByDistrictIdAndActiveTrueOrderByNameAsc(districtId);
    }

    //   Student Save Method
    @PostMapping("/save")
    public String saveStudent(StudentRequestDto studentRequestDto) {
        studentService.saveStudent(studentRequestDto);
        return "redirect:/student/list";
    }

    //   All Student List View
    @GetMapping("/list")
    public String getAllStd(Model model) {
        List<StudentResponseDto> getStudentAll = studentService.getAllStudent();
        model.addAttribute("getStudentAll", getStudentAll);
        model.addAttribute("title", "Student List");
        return "student/studentList";
    }

    @GetMapping("/roll")
    public String getStudentByRollNumber(@RequestParam("rollNumber") int rollNumber, Model model) {
        try {
            StudentResponseDto student = studentService.getByRoll(rollNumber);
            model.addAttribute("getStudentAll", List.of(student)); // List হিসেবে দিতে হবে
        } catch (NoSuchElementException e) {
            model.addAttribute("getStudentAll", List.of());
            model.addAttribute("errorMessage", "No student found with Roll: " + rollNumber);
        }

        model.addAttribute("title", "Student List");
        return "student/studentList"; // Thymeleaf template
    }


}
