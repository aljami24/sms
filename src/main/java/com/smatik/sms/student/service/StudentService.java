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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
        Student student = StudentMapper.mapToStudentEntity(studentRequestDto);

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

        // Upload files
        Helper.studentFilesUpload(uploadDir, studentRequestDto);
        studentRepository.save(student);
    }

    //    All Student Show Method
    public List<StudentResponseDto> getAllStudent() {
        return studentRepository.findAll().stream().map(StudentMapper::mapToStudentResponseDto).collect(Collectors.toList());
    }

    //    Student Filter
    public StudentResponseDto getByRoll(int rollNumber) {
        Student student = studentRepository.findByRoll(rollNumber).orElseThrow();
        return StudentMapper.mapToStudentResponseDto(student);
    }

    //    Student Details Method
    public StudentResponseDto showStudentDetails(Long id) {
        Student student = studentRepository.findById(id).orElseThrow();
        return StudentMapper.mapToStudentResponseDto(student);
    }

    //    Student Delete
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
        Helper.deleteStudentAllFiles(uploadDir, id);
    }


}
