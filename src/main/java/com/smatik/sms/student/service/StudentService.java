package com.smatik.sms.student.service;

import com.smatik.sms.academic.model.entity.ClassroomVersionSection;
import com.smatik.sms.academic.model.repository.ClassroomVersionSectionRepository;
import com.smatik.sms.common.util.Helper;
import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.model.dto.response.StudentResponseDto;
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.student.model.mapper.StudentMapper;
import com.smatik.sms.student.model.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.smatik.sms.common.constants.Constants.STUDENT_NID_DOB_PATH;
import static com.smatik.sms.common.constants.Constants.STUDENT_PHOTO_PATH;

/**
 * StudentService
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassroomVersionSectionRepository classroomVersionSectionRepository;

    @Value("${file.upload-directory}")
    private String uploadDir;

    //   Student Save Method
    @Transactional
    public void saveStudent(StudentRequestDto studentRequestDto) {

        // Map DTO → Entity
        Student student = new Student();
        StudentMapper.mapToStudentEntity(studentRequestDto, student);

        // Get ClassRoom-Version-Section
        ClassroomVersionSection cvs = classroomVersionSectionRepository
                .findByClassRoomIdAndVersionIdAndSectionId(
                        studentRequestDto.getClassRoomId(),
                        studentRequestDto.getVersionId(),
                        studentRequestDto.getSectionId()
                ).orElseThrow(() -> new RuntimeException("Invalid Class-Version-Section"));

        student.setClassroomVersionSectionsId(cvs);

        // Generate unique 4-digit roll BEFORE saving
        int roll;
        do {
            roll = ThreadLocalRandom.current().nextInt(1000, 10000);
        } while (studentRepository.existsByRoll(roll));
        student.setRoll(roll);

        // Save student → ID generate হবে
        studentRepository.save(student);
        studentRepository.flush(); // ID immediately available

        // Set photoDir & nidDir
        student.setPhotoDir(STUDENT_PHOTO_PATH + student.getId());
        student.setNidDir(STUDENT_NID_DOB_PATH + student.getId());

        studentRequestDto.setId(student.getId());

        // Upload files
        Helper.studentFilesUpload(uploadDir, studentRequestDto);
        studentRepository.save(student);
    }

    //   All Student Show Method
    public List<StudentResponseDto> getAllStudent(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        AtomicInteger serialNo = new AtomicInteger(page * pageSize + 1);
        return studentRepository.findAll().stream().map(StudentMapper::mapToStudentResponseDto).collect(Collectors.toList());
    }

    //   Filter Students Method
    public List<StudentResponseDto> filterStudents(
            Integer rollNumber,
            Long classRoomId,
            String section,
            String version,
            int page,
            int pageSize) {

        // Convert empty strings to null for proper JPQL query handling
        String cleanSection = (section != null && section.isEmpty()) ? null : section;
        String cleanVersion = (version != null && version.isEmpty()) ? null : version;

        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Student> studentPage = studentRepository.filterStudents(
                rollNumber, classRoomId, cleanSection, cleanVersion, pageable
        );

        return studentPage.stream()
                .map(StudentMapper::mapToStudentResponseDto)
                .collect(Collectors.toList());
    }

    public long getTotalFilterCount(Integer rollNumber, Long classRoomId, String section, String version) {
        // Convert empty strings to null for proper JPQL query handling
        String cleanSection = (section != null && section.isEmpty()) ? null : section;
        String cleanVersion = (version != null && version.isEmpty()) ? null : version;

        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "id"));
        Page<Student> studentPage = studentRepository.filterStudents(
                rollNumber, classRoomId, cleanSection, cleanVersion, pageable
        );
        return studentPage.getTotalElements();
    }

    //   Student Filter
    public StudentResponseDto getByRoll(int rollNumber) {
        Student student = studentRepository.findByRoll(rollNumber).orElseThrow();
        return StudentMapper.mapToStudentResponseDto(student);
    }

    //   Student Details Method
    public StudentResponseDto showStudentDetails(Long id) {
        Student student = studentRepository.findById(id).orElseThrow();
        return StudentMapper.mapToStudentResponseDto(student);
    }

    //   Student Delete
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
        Helper.deleteStudentAllFiles(uploadDir, id);
    }

    //   Student Update
    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto) {
        Student existingStudent = studentRepository.findById(studentRequestDto.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get ClassRoom-Version-Section
        ClassroomVersionSection cvs = classroomVersionSectionRepository
                .findByClassRoomIdAndVersionIdAndSectionId(
                        studentRequestDto.getClassRoomId(),
                        studentRequestDto.getVersionId(),
                        studentRequestDto.getSectionId()
                ).orElseThrow(() -> new RuntimeException("Invalid Class-Version-Section"));

        StudentMapper.mapToStudentEntity(studentRequestDto, existingStudent);
        // Set CVS after mapper to avoid overwriting with null
        existingStudent.setClassroomVersionSectionsId(cvs);
        Student updatedStudent = studentRepository.save(existingStudent);
        return StudentMapper.mapToStudentResponseDto(updatedStudent);
    }


}
