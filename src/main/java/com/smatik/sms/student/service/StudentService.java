package com.smatik.sms.student.service;

import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.student.model.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
 * StudentService
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Service
public class StudentService {

    public void saveStudent(StudentRequestDto studentRequestDto) {
        Student student = StudentMapper.mapToStudentEntity(studentRequestDto);
    }

}
