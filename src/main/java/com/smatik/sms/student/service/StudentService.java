package com.smatik.sms.student.service;

import com.smatik.sms.academic.model.entity.ClassroomVersionSection;
import com.smatik.sms.academic.model.repository.ClassroomVersionSectionRepository;
import com.smatik.sms.common.util.Helper;
import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.student.model.mapper.StudentMapper;
import com.smatik.sms.student.model.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Value("/home/aljami/Documents/SmhAtik/fileuploadDir")
    private String uploadDir;

    //    Student Save Method............................................................................
    public void saveStudent(StudentRequestDto studentRequestDto) {
        Student student = StudentMapper.mapToStudentEntity(studentRequestDto);

        //    ClassRoom,Version,Section check from db
        ClassroomVersionSection classroomVersionSection = classroomVersionSectionRepository.findByClassRoomIdAndVersionIdAndSectionId(
                studentRequestDto.getClassRoomId(),
                studentRequestDto.getVersionId(),
                studentRequestDto.getSectionId()
        ).orElseThrow(() ->
                new RuntimeException("Invalid Class-Version-Section"));
        student.setClassroomVersionSectionsId(classroomVersionSection);

        studentRepository.save(student);
        studentRequestDto.setId(student.getId());
        Helper.studentFilesUpload(uploadDir, studentRequestDto);

        student.setPhotoDir(STUDENT_PHOTO_PATH + student.getId());
        student.setNidDir(STUDENT_NID_DOB_PATH + student.getId());
        studentRepository.save(student);

    }

}
