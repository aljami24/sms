package com.smha.sms.student.controller;

import com.smha.sms.academic.model.entity.Section;
import com.smha.sms.academic.model.entity.Version;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.academic.model.repository.*;
import com.smha.sms.annotation.PermissionRequired;
import com.smha.sms.common.address.dto.AddressRequestDto;
import com.smha.sms.common.address.entity.District;
import com.smha.sms.common.address.entity.Division;
import com.smha.sms.common.address.entity.PoliceStation;
import com.smha.sms.common.address.repository.DistrictRepository;
import com.smha.sms.common.address.repository.DivisionRepository;
import com.smha.sms.common.address.repository.PoliceStationRepository;
import com.smha.sms.common.enums.AddressType;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import com.smha.sms.student.model.dto.request.StudentRequestDto;
import com.smha.sms.student.model.dto.response.StudentResponseDto;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.mapper.StudentMapper;
import com.smha.sms.student.model.repository.StudentRepository;
import com.smha.sms.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final YearRepository yearRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final PoliceStationRepository policeStationRepository;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final ClassroomVersionSectionRepository classroomVersionSectionRepository;


    //  Student Form Show
    @PermissionRequired("STUDENT_CREATE")
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
        model.addAttribute("years", yearRepository.findAll());
        return "student/studentForm";
    }

    //   Student Save Method
    @PostMapping("/save")
    public String saveStudent(StudentRequestDto studentRequestDto) {
        studentService.saveStudent(studentRequestDto);
        return "redirect:/student/list";
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

    //   Versions Load by Class
    @GetMapping("/versions-by-class")
    @ResponseBody
    public List<Version> getVersionsByClass(@RequestParam("classRoomId") Long classRoomId) {
        return classroomVersionSectionRepository.findDistinctVersionsByClassRoomId(classRoomId);
    }

    //   Sections Load by Class
    @GetMapping("/sections-by-class")
    @ResponseBody
    public List<Section> getSectionsByClass(@RequestParam("classRoomId") Long classRoomId,
                                            @RequestParam(value = "versionId", required = false) Long versionId) {
        if (versionId != null) {
            return classroomVersionSectionRepository.findDistinctSectionsByClassRoomIdAndVersionId(classRoomId, versionId);
        }
        return classroomVersionSectionRepository.findDistinctSectionsByClassRoomId(classRoomId);
    }

    //   All Student List View
    @PermissionRequired("STUDENT_LIST")
    @GetMapping("/list")
    public String getAllStudent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String rollNumber,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) Long className,
            @RequestParam(required = false) Long section,
            @RequestParam(required = false) Long version,
            @RequestParam(required = false) Long division,
            @RequestParam(required = false) Long district,
            @RequestParam(required = false) Long policeStation,
            @RequestParam(required = false) Long yearId,
            Model model) {

        Integer rollNum = (rollNumber != null && !rollNumber.isEmpty()) ? Integer.valueOf(rollNumber) : null;

        Integer registrationNum = (registrationNumber != null && !registrationNumber.isEmpty()) ? Integer.valueOf(registrationNumber) : null;

        // Default year
        Long selectedYearId = yearId;
        if (selectedYearId == null) {
            String currentYear = String.valueOf(java.time.Year.now());
            selectedYearId = yearRepository.findByName(currentYear)
                    .map(Year::getId)
                    .orElse(null);
        }

        // Filter detection (default year ignore)
        boolean hasFilters =
                rollNum != null || registrationNum != null || className != null || section != null || version != null || division != null || district != null || policeStation != null || selectedYearId != null;

        Page<StudentResponseDto> studentPage;

        if (hasFilters) {
            studentPage = studentService.filterStudents(rollNum, registrationNum, className, section, version, selectedYearId, division, district, policeStation, page, pageSize);
        } else {
            studentPage = studentService.getAllStudent(page, pageSize, "id", "DESC");
        }

        model.addAttribute("getStudentAll", studentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", studentPage.getTotalPages());

        // Dropdown data
        model.addAttribute("title", "Student List");
        model.addAttribute("classLoad", classRoomRepository.findAll());
        model.addAttribute("versionLoad", versionRepository.findAll());
        model.addAttribute("sectionLoad", sectionRepository.findAll());
        model.addAttribute("years", yearRepository.findAll());
        model.addAttribute("divisionLoad", divisionRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
        model.addAttribute("districtLoad", districtRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
        model.addAttribute("policeStationLoad", policeStationRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));

        // Preserve filters
        model.addAttribute("rollNumber", rollNumber);
        model.addAttribute("registrationNumber", registrationNumber);
        model.addAttribute("className", className);
        model.addAttribute("section", section);
        model.addAttribute("version", version);
        model.addAttribute("yearId", selectedYearId);
        model.addAttribute("division", division);
        model.addAttribute("district", district);
        model.addAttribute("policeStation", policeStation);

        return "student/studentList";
    }


    //    Student Details
    @PermissionRequired("STUDENT_VIEW")
    @GetMapping("/details/{id}")
    public String showStdDetails(@PathVariable Long id, Model model) {
        StudentResponseDto showStudentDetail = studentService.showStudentDetails(id);
        model.addAttribute("showStudentDetail", showStudentDetail);
        model.addAttribute("title", "Student Details");
        return "student/studentDetails";

    }

    @PermissionRequired("STUDENT_DELETE")
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/student/list";
    }

    //    Update Form Show
    @PermissionRequired("STUDENT_UPDATE")
    @GetMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int pageSize,
                                Model model) {

        Student student = studentRepository.findById(id).orElseThrow();
        StudentRequestDto studentRequestDto = StudentMapper.mapToStudentRequestDto(student);

        model.addAttribute("studentForm", studentRequestDto);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("identityTypes", IdentityType.values());
        model.addAttribute("addressTypes", AddressType.values());
        model.addAttribute("title", "Update Student");
        model.addAttribute("divisions", divisionRepository.findAll());
        model.addAttribute("districts", districtRepository.findAll());
        model.addAttribute("policeStations", policeStationRepository.findAll());
        model.addAttribute("classRoom", classRoomRepository.findAll());
        model.addAttribute("version", versionRepository.findAll());
        model.addAttribute("section", sectionRepository.findAll());
        model.addAttribute("years", yearRepository.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        return "student/studentForm";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("studentForm") StudentRequestDto studentRequestDto,
                                @RequestParam(defaultValue = "0") int page,
                                BindingResult bindingResult, Model model,
                                @RequestParam(defaultValue = "10") int pageSize) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genders", Gender.values());
            model.addAttribute("identityTypes", IdentityType.values());
            model.addAttribute("addressTypes", AddressType.values());
            model.addAttribute("title", "Update Student");
            // If there are errors, the page and pageSize are added back to the form
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", pageSize);
            return "/student/studentForm";
        }
        studentService.updateStudent(studentRequestDto);
        return "redirect:/student/list?page=" + page + "&pageSize=" + pageSize + "#student-" + id;
    }

    //    Old Student Admission - Search by Registration
    @PermissionRequired("OLD_STUDENT_CREATE")
    @GetMapping("/old-admission")
    public String searchOldStudent(@RequestParam("registration") int registration, Model model) {
        try {
            StudentResponseDto student = studentService.getByRegistration(registration);
            model.addAttribute("student", student);
            model.addAttribute("classRoom", classRoomRepository.findAll());
            model.addAttribute("version", versionRepository.findAll());
            model.addAttribute("section", sectionRepository.findAll());
            model.addAttribute("years", yearRepository.findAll());
            model.addAttribute("title", "Old Student Admission");
            return "student/oldAdmission";
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "No student found with Registration: " + registration);
            return "redirect:/student/list?error=" + "No student found with Registration: " + registration;
        }
    }

    //    Old Student Admission - Confirm Admission
    @PostMapping("/confirm-admission")
    public String confirmOldAdmission(
            @RequestParam("studentId") Long studentId,
            @RequestParam("yearId") Long yearId,
            @RequestParam("classRoomId") Long classRoomId,
            @RequestParam(value = "sectionId", required = false) String sectionIdStr,
            @RequestParam(value = "versionId", required = false) String versionIdStr) {

        // Convert empty strings to null
        Long sectionId = (sectionIdStr != null && !sectionIdStr.isEmpty()) ? Long.valueOf(sectionIdStr) : null;
        Long versionId = (versionIdStr != null && !versionIdStr.isEmpty()) ? Long.valueOf(versionIdStr) : null;

        studentService.admitOldStudent(studentId, yearId, classRoomId, sectionId, versionId);
        return "redirect:/student/list";
    }


}
