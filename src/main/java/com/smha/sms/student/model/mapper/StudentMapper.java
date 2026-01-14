package com.smha.sms.student.model.mapper;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.common.address.dto.AddressRequestDto;
import com.smha.sms.common.address.dto.AddressResponseDto;
import com.smha.sms.common.address.entity.Address;
import com.smha.sms.student.model.dto.request.StudentRequestDto;
import com.smha.sms.student.model.dto.response.StudentResponseDto;
import com.smha.sms.student.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentMapper
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

public class StudentMapper {

    public static void mapToStudentEntity(StudentRequestDto studentRequestDto, Student student) {

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
        // Note: classroomVersionSectionsId is set in the service layer, not here


        if (student.getAddresses() == null) {
            // SAVE case (new entity)
            student.setAddresses(new ArrayList<>());
        } else {
            // UPDATE case (managed entity)
            student.getAddresses().clear();
        }

        for (AddressRequestDto addressRequestDto : studentRequestDto.getAddresses()) {
            Address address = new Address();
            address.setVillage(addressRequestDto.getVillage());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setDistrict(addressRequestDto.getDistrict());
            address.setDivision(addressRequestDto.getDivision());
            address.setPoliceStation(addressRequestDto.getPoliceStation());
            address.setStudent(student);
            student.getAddresses().add(address);
        }
    }

    public static StudentResponseDto mapToStudentResponseDto(Student student) {
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setFatherName(student.getFatherName());
        studentResponseDto.setMotherName(student.getMotherName());
        studentResponseDto.setDob(student.getDob());
        studentResponseDto.setGender(student.getGender());
        studentResponseDto.setRoll(student.getRoll());
        studentResponseDto.setClassroomVersionSectionsId(student.getClassroomVersionSectionsId());
        studentResponseDto.setPhotoDir(student.getPhotoDir());
        studentResponseDto.setNidDir(student.getNidDir());

        List<AddressResponseDto> addressList = new ArrayList<>();

        for (Address address : student.getAddresses()) {
            AddressResponseDto addressResponseDto = new AddressResponseDto();
            addressResponseDto.setVillage(address.getVillage());
            addressResponseDto.setAddressType(address.getAddressType());
            addressResponseDto.setDistrict(address.getDistrict());
            addressResponseDto.setDivision(address.getDivision());
            addressResponseDto.setPoliceStation(address.getPoliceStation());
            addressList.add(addressResponseDto);
        }
        studentResponseDto.setAddresses(addressList);
        return studentResponseDto;
    }

    public static StudentRequestDto mapToStudentRequsetDto(Student student) {

        StudentRequestDto studentRequestDto = new StudentRequestDto();

        studentRequestDto.setId(student.getId());
        studentRequestDto.setName(student.getName());
        studentRequestDto.setDob(student.getDob());
        studentRequestDto.setGender(student.getGender());
        studentRequestDto.setFatherName(student.getFatherName());
        studentRequestDto.setMotherName(student.getMotherName());
        studentRequestDto.setIdentityNumber(student.getIdentityNumber());
        studentRequestDto.setIdentityType(student.getIdentityType());
        studentRequestDto.setPhotoDir(student.getPhotoDir());
        studentRequestDto.setNidDir(student.getNidDir());
        studentRequestDto.setClassroomVersionSectionsId(student.getClassroomVersionSectionsId());

        ClassroomVersionSection cvs =
                student.getClassroomVersionSectionsId();

        if (cvs != null) {
            studentRequestDto.setClassRoomId(cvs.getClassRoom().getId());
            studentRequestDto.setVersionId(cvs.getVersion().getId());
            // Section can be null for classes 6-8
            studentRequestDto.setSectionId(cvs.getSection() != null ? cvs.getSection().getId() : null);
        }

        List<AddressRequestDto> addressList = new ArrayList<>();

        for (Address address : student.getAddresses()) {
            AddressRequestDto addressRequestDto = new AddressRequestDto();
            addressRequestDto.setVillage(address.getVillage());
            addressRequestDto.setAddressType(address.getAddressType());
            addressRequestDto.setDistrict(address.getDistrict());
            addressRequestDto.setDivision(address.getDivision());
            addressRequestDto.setPoliceStation(address.getPoliceStation());
            addressRequestDto.setStudent(student);
            addressList.add(addressRequestDto);
        }
        studentRequestDto.setAddresses(addressList);
        return studentRequestDto;
    }


}
