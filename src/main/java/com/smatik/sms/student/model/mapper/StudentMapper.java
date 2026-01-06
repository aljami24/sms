package com.smatik.sms.student.model.mapper;

import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.address.entity.Address;
import com.smatik.sms.student.model.dto.request.StudentRequestDto;
import com.smatik.sms.student.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentMapper
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

public class StudentMapper {

    public static Student mapToStudentEntity(StudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setId(studentRequestDto.getId());
        student.setName(studentRequestDto.getName());
        student.setDob(studentRequestDto.getDob());
        student.setGender(studentRequestDto.getGender());
        student.setFatherName(studentRequestDto.getFatherName());
        student.setMotherName(studentRequestDto.getMotherName());
        student.setIdentityNumber(studentRequestDto.getIdentityNumber());
        student.setIdentityType(studentRequestDto.getIdentityType());
        student.setPhotoDir(studentRequestDto.getPhotoDir());
        student.setNidDir(studentRequestDto.getNidDir());
        student.setClassroomVersionSectionsId(studentRequestDto.getClassroomVersionSectionsId());

        List<Address> addressList = new ArrayList<>();

        for (AddressRequestDto addressRequestDto : studentRequestDto.getAddresses()) {
            Address address = new Address();
            address.setVillage(addressRequestDto.getVillage());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setDistrict(addressRequestDto.getDistrict());
            address.setDivision(addressRequestDto.getDivision());
            address.setPoliceStation(addressRequestDto.getPoliceStation());
            address.setStudent(student);
            addressList.add(address);
        }
        student.setAddresses(addressList);
        return student;
    }
}
