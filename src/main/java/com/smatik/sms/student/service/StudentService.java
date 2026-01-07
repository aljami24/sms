package com.smatik.sms.student.service;

import com.smatik.sms.common.util.Helper;
import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.student.model.mapper.StudentMapper;
import com.smatik.sms.student.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentService {

    @Value("/home/aljami/Documents/SmhAtik/fileuploadDir")
    private String uploadDir;
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(StudentRequestDto studentRequestDto) {
        Student student = StudentMapper.mapToStudentEntity(studentRequestDto);
        studentRepository.save(student);
        studentRequestDto.setId(student.getId());
        Helper.studentFilesUpload(uploadDir, studentRequestDto);

        student.setPhotoDir(STUDENT_PHOTO_PATH + student.getId());
        student.setNidDir(STUDENT_NID_DOB_PATH + student.getId());
        studentRepository.save(student);

    }

}
