package com.smha.sms.student.service;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.academic.model.repository.ClassroomVersionSectionRepository;
import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.annotation.Audit;
import com.smha.sms.common.util.Helper;
import com.smha.sms.student.model.dto.request.StudentRequestDto;
import com.smha.sms.student.model.dto.response.StudentResponseDto;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.entity.StudentAcademicRecord;
import com.smha.sms.student.model.mapper.StudentMapper;
import com.smha.sms.student.model.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final YearRepository yearRepository;
    private final com.smha.sms.common.repository.AuditLogRepository auditLogRepository;

    @Value("${file.upload-directory}")
    private String uploadDir;

    //   Student Save Method
    @Transactional
    @Audit(permission = "Student_Create")
    public Student saveStudent(StudentRequestDto studentRequestDto) {

        // Map DTO → Entity
        Student student = new Student();
        StudentMapper.mapToStudentEntity(studentRequestDto, student);

        // Get year
        Year year = yearRepository.findById(studentRequestDto.getYearId())
                .orElseThrow(() -> new RuntimeException("Year not found"));

        // Generate 9-digit registration: Year (4 digits) + 5 random digits
        int yearValue = Integer.parseInt(year.getName());
        int registration;
        do {
            // Generate 5 random digits (10000 to 99999)
            int randomDigits = ThreadLocalRandom.current().nextInt(10000, 100000);
            // Combine: year * 100000 + randomDigits to get 9-digit number
            registration = (yearValue * 100000) + randomDigits;
        } while (studentRepository.existsByRegistration(registration));
        student.setRegistration(registration);

        // Get ClassRoom-Version-Section
        ClassroomVersionSection cvs = classroomVersionSectionRepository
                .findByClassRoomIdAndVersionIdAndSectionId(
                        studentRequestDto.getClassRoomId(),
                        studentRequestDto.getVersionId(),
                        studentRequestDto.getSectionId()
                ).orElseThrow(() -> new RuntimeException("Invalid Class-Version-Section"));

        // Generate sequential roll number based on class, version, and YEAR
        Integer maxRoll = studentRepository.findMaxRollByClassRoomAndVersion(
                studentRequestDto.getClassRoomId(),
                studentRequestDto.getVersionId(),
                year.getId()
        );
        int nextRoll = (maxRoll == null ? 0 : maxRoll) + 1;

        // Add academic record with roll only (registration is on student)
        StudentMapper.addAcademicRecordToStudent(student, cvs, year.getId(), nextRoll);

        // Save student → ID generate হবে
        studentRepository.save(student);
        studentRepository.flush(); // ID immediately available

        // Set photoDir & nidDir
        student.setPhotoDir(STUDENT_PHOTO_PATH + student.getId());
        student.setNidDir(STUDENT_NID_DOB_PATH + student.getId());

        studentRequestDto.setId(student.getId());

        // Upload files
        Helper.studentFilesUpload(uploadDir, studentRequestDto);
        return studentRepository.save(student);
    }

    //   All Student Show Method
    public Page<StudentResponseDto> getAllStudent(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);
        PageRequest pageable = PageRequest.of(page, pageSize, sort);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(StudentMapper::mapToStudentResponseDto);
    }

    //   Student Filter
    public Page<StudentResponseDto> filterStudents(
            Integer rollNumber,
            Integer registrationNumber,
            Long classRoomId,
            Long section,
            Long version,
            Long yearId,
            Long division,
            Long district,
            Long policeStation,
            int page,
            int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Page<Student> studentPage = studentRepository.filterStudents(rollNumber, registrationNumber, classRoomId, section, version, yearId, division, district, policeStation, pageable);

        return studentPage.map(student ->
                StudentMapper.mapToStudentResponseDto(student, yearId));
    }

    //    For Old Student Search
    public StudentResponseDto getByRegistration(int registrationNumber) {
        Student student = studentRepository.findByRegistration(registrationNumber).orElseThrow();
        return StudentMapper.mapToStudentResponseDto(student);
    }

    //   Student Details Method
    public StudentResponseDto showStudentDetails(Long id) {
        Student student = studentRepository.findById(id).orElseThrow();
        return StudentMapper.mapToStudentResponseDto(student);
    }

    //   Get Audit Logs for Student
    public java.util.List<com.smha.sms.common.entity.AuditLog> getStudentAuditLogs(Long studentId) {
        return auditLogRepository.findByStudentId(studentId);
    }

    //   Student Delete
    @Audit(permission = "Student_Delete")
    public Student deleteById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
        Helper.deleteStudentAllFiles(uploadDir, id);
        studentRepository.deleteById(id);
        return student;
    }

    //   Student Update
    @Transactional
    @Audit(permission = "Student_Update")
    public Student updateStudent(StudentRequestDto studentRequestDto) {
        Student existingStudent = studentRepository.findById(studentRequestDto.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Find the most recent year from academic records
        Integer mostRecentYear = existingStudent.getStudentAcademicRecords().stream()
                .filter(ar -> ar.getYear() != null)
                .map(ar -> Integer.parseInt(ar.getYear().getName()))
                .max(Integer::compareTo)
                .orElse(null);

        // Get the year being updated
        Year selectedYear = yearRepository.findById(studentRequestDto.getYearId())
                .orElseThrow(() -> new RuntimeException("Year not found"));
        int selectedYearValue = Integer.parseInt(selectedYear.getName());

        // Validate: Only the most recent year's academic record can be updated
        if (mostRecentYear != null && selectedYearValue < mostRecentYear) {
            throw new RuntimeException("Cannot update academic information for previous years. Only the most recent year's record can be modified.");
        }

        // Preserve existing document paths
        String existingPhotoDir = existingStudent.getPhotoDir();
        String existingNidDir = existingStudent.getNidDir();

        // Check if new files are being uploaded
        boolean hasNewPhoto = studentRequestDto.getPhoto() != null && !studentRequestDto.getPhoto().isEmpty();
        boolean hasNewNid = studentRequestDto.getNid() != null && !studentRequestDto.getNid().isEmpty();

        // Upload new files if provided
        if (hasNewPhoto || hasNewNid) {
            Helper.studentFilesUpload(uploadDir, studentRequestDto);
        }

        // Get ClassRoom-Version-Section
        ClassroomVersionSection cvs = classroomVersionSectionRepository
                .findByClassRoomIdAndVersionIdAndSectionId(
                        studentRequestDto.getClassRoomId(),
                        studentRequestDto.getVersionId(),
                        studentRequestDto.getSectionId()
                ).orElseThrow(() -> new RuntimeException("Invalid Class-Version-Section"));

        // Check if academic record already exists for this year
        if (existingStudent.getStudentAcademicRecords() != null) {
            boolean recordExistsForYear = existingStudent.getStudentAcademicRecords().stream()
                    .anyMatch(record -> record.getYear() != null && record.getYear().getId().equals(studentRequestDto.getYearId()));

            if (recordExistsForYear) {
                // Update existing record instead of adding a new one
                existingStudent.getStudentAcademicRecords().stream()
                        .filter(record -> record.getYear() != null && record.getYear().getId().equals(studentRequestDto.getYearId()))
                        .findFirst()
                        .ifPresent(record -> record.setClassroomVersionSection(cvs));
            } else {
                // Add new academic record
                StudentMapper.addAcademicRecordToStudent(existingStudent, cvs, studentRequestDto.getYearId());
            }
        } else {
            // Add new academic record
            StudentMapper.addAcademicRecordToStudent(existingStudent, cvs, studentRequestDto.getYearId());
        }

        // Map basic fields and addresses (this may overwrite photoDir and nidDir)
        StudentMapper.mapToStudentEntity(studentRequestDto, existingStudent);

        // Restore original paths if no new file was uploaded
        if (!hasNewPhoto) {
            existingStudent.setPhotoDir(existingPhotoDir);
        }
        if (!hasNewNid) {
            existingStudent.setNidDir(existingNidDir);
        }

        studentRepository.save(existingStudent);
        return existingStudent;
    }

    //   Old Student Admission - Create New Academic Record
    @Transactional
    @Audit(permission = "Old_Student_Create")
    public Student admitOldStudent(Long studentId, Long yearId, Long classRoomId,
                                Long sectionId, Long versionId) {
        // Find the student
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Check if academic record already exists for this year
        if (student.getStudentAcademicRecords() != null) {
            boolean recordExistsForYear = student.getStudentAcademicRecords().stream()
                    .anyMatch(record -> record.getYear() != null && record.getYear().getId().equals(yearId));

            if (recordExistsForYear) {
                throw new RuntimeException("Student already has an academic record for this year. Please update the existing record instead.");
            }
        }

        // Find the year entity
        Year year = yearRepository.findById(yearId)
                .orElseThrow(() -> new RuntimeException("Year not found"));

        // Get ClassRoom-Version-Section combination
        ClassroomVersionSection cvs = classroomVersionSectionRepository
                .findByClassRoomIdAndVersionIdAndSectionId(classRoomId, versionId, sectionId)
                .orElseThrow(() -> new RuntimeException("Invalid Class-Version-Section combination"));

        // Generate sequential roll number based on class, version, and YEAR
        Integer maxRoll = studentRepository.findMaxRollByClassRoomAndVersion(classRoomId, versionId, year.getId());
        int nextRoll = (maxRoll == null ? 0 : maxRoll) + 1;

        // Create new academic record with roll only (registration stays on student)
        if (student.getStudentAcademicRecords() == null) {
            student.setStudentAcademicRecords(new ArrayList<>());
        }

        StudentAcademicRecord academicRecord = new StudentAcademicRecord();
        academicRecord.setStudent(student);
        academicRecord.setClassroomVersionSection(cvs);
        academicRecord.setYear(year);
        academicRecord.setRoll(nextRoll);

        student.getStudentAcademicRecords().add(academicRecord);

        // Save the student with new academic record
        return studentRepository.save(student);
    }


}
