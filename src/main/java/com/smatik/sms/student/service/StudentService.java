package com.smatik.sms.student.service;

import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.student.model.mapper.StudentMapper;
import com.smatik.sms.student.model.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * StudentService
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudent(StudentRequestDto studentRequestDto) {
        Student student = StudentMapper.mapToStudentEntity(studentRequestDto);
        studentRepository.save(student);
    }

}
