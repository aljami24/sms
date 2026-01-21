package com.smha.sms.student.service;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.repository.ClassroomVersionSectionRepository;
import com.smha.sms.common.util.Helper;
import com.smha.sms.student.model.dto.request.StudentRequestDto;
import com.smha.sms.student.model.dto.response.StudentResponseDto;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.mapper.StudentMapper;
import com.smha.sms.student.model.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

import static com.smha.sms.common.constants.Constants.STUDENT_NID_DOB_PATH;
import static com.smha.sms.common.constants.Constants.STUDENT_PHOTO_PATH;

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

        // Add academic record from the form selection
        StudentMapper.addAcademicRecordToStudent(student, cvs, studentRequestDto.getYearId());

        // Generate unique 6-digit regi BEFORE saving
        int regiNo;
        do {
            regiNo = ThreadLocalRandom.current().nextInt(100000, 1000000);
        } while (studentRepository.existsByRoll(regiNo));
        student.setRoll(regiNo);

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
    public Page<StudentResponseDto> getAllStudent(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(StudentMapper::mapToStudentResponseDto);
    }

    //   Filter Students Method
    public Page<StudentResponseDto> filterStudents(
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

        return studentPage
                .map(StudentMapper::mapToStudentResponseDto);
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
    @Transactional
    public void updateStudent(StudentRequestDto studentRequestDto) {
        Student existingStudent = studentRepository.findById(studentRequestDto.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Preserve existing document paths if not being updated
        String existingPhotoDir = existingStudent.getPhotoDir();
        String existingNidDir = existingStudent.getNidDir();

        // Get ClassRoom-Version-Section
        ClassroomVersionSection cvs = classroomVersionSectionRepository
                .findByClassRoomIdAndVersionIdAndSectionId(
                        studentRequestDto.getClassRoomId(),
                        studentRequestDto.getVersionId(),
                        studentRequestDto.getSectionId()
                ).orElseThrow(() -> new RuntimeException("Invalid Class-Version-Section"));

        // Map basic fields and addresses
        StudentMapper.mapToStudentEntity(studentRequestDto, existingStudent);

        // Add/update academic record from the form selection
        StudentMapper.addAcademicRecordToStudent(existingStudent, cvs, studentRequestDto.getYearId());

        // Restore document paths if they were null in the request (not being updated)
        if (studentRequestDto.getPhotoDir() == null || studentRequestDto.getPhotoDir().isEmpty()) {
            existingStudent.setPhotoDir(existingPhotoDir);
        }
        if (studentRequestDto.getNidDir() == null || studentRequestDto.getNidDir().isEmpty()) {
            existingStudent.setNidDir(existingNidDir);
        }

        Student updatedStudent = studentRepository.save(existingStudent);
        StudentMapper.mapToStudentResponseDto(updatedStudent);
    }


}
