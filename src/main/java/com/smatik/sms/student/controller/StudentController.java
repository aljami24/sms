package com.smatik.sms.student.controller;

import com.smatik.sms.academic.model.entity.Section;
import com.smatik.sms.academic.model.entity.Version;
import com.smatik.sms.academic.model.repository.ClassRoomRepository;
import com.smatik.sms.academic.model.repository.ClassroomVersionSectionRepository;
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
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.student.model.mapper.StudentMapper;
import com.smatik.sms.student.model.repository.StudentRepository;
import com.smatik.sms.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final PoliceStationRepository policeStationRepository;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final ClassroomVersionSectionRepository classroomVersionSectionRepository;


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

    //   Versions Load by Class (Cascading Dropdown)
    @GetMapping("/versions-by-class")
    @ResponseBody
    public List<Version> getVersionsByClass(@RequestParam("classRoomId") Long classRoomId) {
        return classroomVersionSectionRepository.findDistinctVersionsByClassRoomId(classRoomId);
    }

    //   Sections Load by Class (Cascading Dropdown)
    @GetMapping("/sections-by-class")
    @ResponseBody
    public List<Section> getSectionsByClass(@RequestParam("classRoomId") Long classRoomId,
                                             @RequestParam(value = "versionId", required = false) Long versionId) {
        if (versionId != null) {
            return classroomVersionSectionRepository.findDistinctSectionsByClassRoomIdAndVersionId(classRoomId, versionId);
        }
        return classroomVersionSectionRepository.findDistinctSectionsByClassRoomId(classRoomId);
    }

    //   Student Save Method
    @PostMapping("/save")
    public String saveStudent(StudentRequestDto studentRequestDto) {
        studentService.saveStudent(studentRequestDto);
        return "redirect:/student/list";
    }

    //   All Student List View
    @GetMapping("/list")
    public String getAllStd(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int pageSize,
                            @RequestParam(required = false) String rollNumber,
                            @RequestParam(required = false) Long className,
                            @RequestParam(required = false) String section,
                            @RequestParam(required = false) String version,
                            Model model) {

        List<StudentResponseDto> getStudentAll;
        long totalPages;

        // Convert empty strings to null for proper filtering
        String cleanSection = (section != null && section.isEmpty()) ? null : section;
        String cleanVersion = (version != null && version.isEmpty()) ? null : version;
        Integer rollNum = (rollNumber != null && !rollNumber.isEmpty()) ? Integer.valueOf(rollNumber) : null;

        // Check if any filter is applied
        boolean hasFilters = rollNum != null || className != null || cleanSection != null || cleanVersion != null;

        if (hasFilters) {
            // Apply filters
            getStudentAll = studentService.filterStudents(
                    rollNum, className, cleanSection, cleanVersion, page, pageSize);

            long totalElements = studentService.getTotalFilterCount(
                    rollNum, className, cleanSection, cleanVersion);
            totalPages = (long) Math.ceil((double) totalElements / pageSize);
        } else {
            // No filters - get all students
            Pageable pageable = PageRequest.of(page, pageSize);
            Page<Student> studentPage = studentRepository.findAll(pageable);
            totalPages = studentPage.getTotalPages();

            getStudentAll = studentService.getAllStudent(
                    page, pageSize, "id", "DESC");
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("getStudentAll", getStudentAll);
        model.addAttribute("title", "Student List");
        model.addAttribute("classRoom", classRoomRepository.findAll());

        // Preserve filter values
        model.addAttribute("rollNumber", rollNumber);
        model.addAttribute("className", className);
        model.addAttribute("section", section);
        model.addAttribute("version", version);
        return "student/studentList";
    }

    //    Student Filter
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

    //    Student Details
    @GetMapping("/details/{id}")
    public String showStdDetails(@PathVariable Long id, Model model) {
        StudentResponseDto showStudentDetail = studentService.showStudentDetails(id);
        model.addAttribute("showStudentDetail", showStudentDetail);
        model.addAttribute("title", "Student Details");
        return "student/studentDetails";

    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/student/list";
    }

    @GetMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int pageSize,
                                Model model) {

        Student student = studentRepository.findById(id).orElseThrow();
        StudentRequestDto studentRequestDto = StudentMapper.mapToStudentRequsetDto(student);

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
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        return "student/studentForm";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute("studentForm") StudentRequestDto studentRequestDto,
                                @RequestParam(defaultValue = "0") int page,
                                BindingResult bindingResult, Model model,
                                @RequestParam(defaultValue = "5") int pageSize) {
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

}
