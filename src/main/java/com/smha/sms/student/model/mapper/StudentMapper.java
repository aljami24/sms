package com.smha.sms.student.model.mapper;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.common.address.dto.AddressRequestDto;
import com.smha.sms.common.address.dto.AddressResponseDto;
import com.smha.sms.common.address.entity.Address;
import com.smha.sms.student.model.dto.request.StudentRequestDto;
import com.smha.sms.student.model.dto.response.StudentResponseDto;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.entity.StudentAcademicRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentMapper
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

public class StudentMapper {

    // ======================== Basic Field Mappers ========================

    /**
     * Maps basic student fields from RequestDto to Entity
     */
    private static void mapBasicFields(StudentRequestDto dto, Student student) {
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setDob(dto.getDob());
        student.setGender(dto.getGender());
        student.setFatherName(dto.getFatherName());
        student.setMotherName(dto.getMotherName());
        student.setIdentityNumber(dto.getIdentityNumber());
        student.setIdentityType(dto.getIdentityType());
        student.setPhotoDir(dto.getPhotoDir());
        student.setNidDir(dto.getNidDir());
    }

    /**
     * Maps basic student fields from Entity to ResponseDto
     */
    private static void mapBasicFields(Student student, StudentResponseDto dto) {
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setFatherName(student.getFatherName());
        dto.setMotherName(student.getMotherName());
        dto.setDob(student.getDob());
        dto.setGender(student.getGender());
        dto.setRoll(student.getRoll());
        dto.setRegistration(student.getRegistration());
        dto.setPhotoDir(student.getPhotoDir());
        dto.setNidDir(student.getNidDir());
    }

    /**
     * Maps basic student fields from Entity to RequestDto (for edit form)
     */
    private static void mapBasicFieldsToDto(Student student, StudentRequestDto dto) {
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDob(student.getDob());
        dto.setGender(student.getGender());
        dto.setFatherName(student.getFatherName());
        dto.setMotherName(student.getMotherName());
        dto.setIdentityNumber(student.getIdentityNumber());
        dto.setIdentityType(student.getIdentityType());
        dto.setPhotoDir(student.getPhotoDir());
        dto.setNidDir(student.getNidDir());
    }

    // ======================== Address Mapping ========================

    /**
     * Maps addresses from RequestDto to Entity
     */
    private static void mapAddressesToEntity(StudentRequestDto studentRequestDto, Student student) {
        if (student.getAddresses() == null) {
            student.setAddresses(new ArrayList<>());
        } else {
            student.getAddresses().clear();
        }

        for (AddressRequestDto addressDto : studentRequestDto.getAddresses()) {
            Address address = new Address();
            address.setVillage(addressDto.getVillage());
            address.setAddressType(addressDto.getAddressType());
            address.setDistrict(addressDto.getDistrict());
            address.setDivision(addressDto.getDivision());
            address.setPoliceStation(addressDto.getPoliceStation());
            address.setStudent(student);
            student.getAddresses().add(address);
        }
    }

    /**
     * Maps addresses from Entity to ResponseDto
     */
    private static List<AddressResponseDto> mapAddressesToResponseDto(Student student) {
        List<AddressResponseDto> addressList = new ArrayList<>();
        for (Address address : student.getAddresses()) {
            AddressResponseDto addressDto = new AddressResponseDto();
            addressDto.setVillage(address.getVillage());
            addressDto.setAddressType(address.getAddressType());
            addressDto.setDistrict(address.getDistrict());
            addressDto.setDivision(address.getDivision());
            addressDto.setPoliceStation(address.getPoliceStation());
            addressList.add(addressDto);
        }
        return addressList;
    }

    /**
     * Maps addresses from Entity to RequestDto (for edit form)
     */
    private static List<AddressRequestDto> mapAddressesToRequestDto(Student student) {
        List<AddressRequestDto> addressList = new ArrayList<>();
        for (Address address : student.getAddresses()) {
            AddressRequestDto addressDto = new AddressRequestDto();
            addressDto.setVillage(address.getVillage());
            addressDto.setAddressType(address.getAddressType());
            addressDto.setDistrict(address.getDistrict());
            addressDto.setDivision(address.getDivision());
            addressDto.setPoliceStation(address.getPoliceStation());
            addressList.add(addressDto);
        }
        return addressList;
    }

    // ======================== Academic Record Mapping ========================

    /**
     * Creates a StudentAcademicRecord from the class/version/section/year selection
     * This is called from the service after finding the ClassroomVersionSection
     */
    public static void addAcademicRecordToStudent(Student student, ClassroomVersionSection cvs, Long yearId) {
        if (student.getStudentAcademicRecords() == null) {
            student.setStudentAcademicRecords(new ArrayList<>());
        }

        StudentAcademicRecord academicRecord = new StudentAcademicRecord();
        academicRecord.setStudent(student);
        academicRecord.setClassroomVersionSection(cvs);

        if (yearId != null) {
            Year year = new Year();
            year.setId(yearId);
            academicRecord.setYear(year);
        }

        student.getStudentAcademicRecords().add(academicRecord);
    }

    /**
     * Extracts class/version/section/year IDs from academic record for ResponseDto
     */

    private static void extractAcademicInfoForResponse(Student student, StudentResponseDto studentResponseDto) {
        extractAcademicInfoForResponse(student, studentResponseDto, null);
    }

    /**
     * Extracts class/version/section/year IDs from academic record for ResponseDto
     * If yearId is provided, finds the academic record matching that year
     */
    private static void extractAcademicInfoForResponse(Student student, StudentResponseDto studentResponseDto, Long yearId) {
        if (student.getStudentAcademicRecords() != null && !student.getStudentAcademicRecords().isEmpty()) {
            // Find the academic record matching the yearId, or get the first one if no yearId specified
            StudentAcademicRecord record = student.getStudentAcademicRecords().stream()
                    .filter(ar -> yearId == null || (ar.getYear() != null && ar.getYear().getId().equals(yearId)))
                    .findFirst()
                    .orElse(student.getStudentAcademicRecords().get(0));

            if (record.getClassroomVersionSection() != null) {
                ClassroomVersionSection cvs = record.getClassroomVersionSection();
                studentResponseDto.setClassRoomId(cvs.getClassRoom().getId());
                studentResponseDto.setClassRoomName(cvs.getClassRoom().getName());
                studentResponseDto.setVersionId(cvs.getVersion().getId());
                studentResponseDto.setVersionName(cvs.getVersion().getName());
                if (cvs.getSection() != null) {
                    studentResponseDto.setSectionId(cvs.getSection().getId());
                    studentResponseDto.setSectionName(cvs.getSection().getName());
                }
            }
            if (record.getYear() != null) {
                studentResponseDto.setYearName(record.getYear().getName());
            }
        }
    }

    /**
     * Extracts class/version/section/year IDs from academic record for RequestDto (edit form)
     */
    private static void extractAcademicInfoForRequest(Student student, StudentRequestDto studentRequestDto) {
        if (student.getStudentAcademicRecords() != null && !student.getStudentAcademicRecords().isEmpty()) {
            StudentAcademicRecord record = student.getStudentAcademicRecords().get(0);
            if (record.getClassroomVersionSection() != null) {
                ClassroomVersionSection cvs = record.getClassroomVersionSection();
                studentRequestDto.setClassRoomId(cvs.getClassRoom().getId());
                studentRequestDto.setVersionId(cvs.getVersion().getId());
                if (cvs.getSection() != null) {
                    studentRequestDto.setSectionId(cvs.getSection().getId());
                }
            }
            if (record.getYear() != null) {
                studentRequestDto.setYearId(record.getYear().getId());
            }
        }
    }

    // ======================== Public Mapping Methods ========================

    /**
     * Maps StudentRequestDto to Student Entity
     * Used for both create and update operations
     */
    public static void mapToStudentEntity(StudentRequestDto studentRequestDto, Student student) {
        mapBasicFields(studentRequestDto, student);
        mapAddressesToEntity(studentRequestDto, student);
    }

    /**
     * Maps Student Entity to StudentResponseDto
     * Used for displaying student data in templates
     */
    public static StudentResponseDto mapToStudentResponseDto(Student student) {
        return mapToStudentResponseDto(student, null);
    }

    /**
     * Maps Student Entity to StudentResponseDto with specific year filter
     * Used for displaying student data in templates when filtering by year
     */
    public static StudentResponseDto mapToStudentResponseDto(Student student, Long yearId) {
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        mapBasicFields(student, studentResponseDto);
        extractAcademicInfoForResponse(student, studentResponseDto, yearId);
        studentResponseDto.setAddresses(mapAddressesToResponseDto(student));
        return studentResponseDto;
    }

    /**
     * Maps Student Entity to StudentRequestDto
     * Used for populating edit form
     */
    public static StudentRequestDto mapToStudentRequestDto(Student student) {
        StudentRequestDto studentRequestDto = new StudentRequestDto();
        mapBasicFieldsToDto(student, studentRequestDto);
        extractAcademicInfoForRequest(student, studentRequestDto);
        studentRequestDto.setAddresses(mapAddressesToRequestDto(student));
        return studentRequestDto;
    }
}
