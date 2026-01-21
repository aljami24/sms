package com.smha.sms.student.model.dto.response;

import com.smha.sms.common.address.dto.AddressResponseDto;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import com.smha.sms.student.model.dto.request.FileUpload;
import com.smha.sms.student.model.entity.StudentAcademicRecord;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentResponseDto
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Getter
@Setter
public class StudentResponseDto extends FileUpload {
    private Long id;
    private String name;
    private Integer roll;
    private Integer registration;
    private String fatherName;
    private String motherName;
    private Gender gender;
    private LocalDate dob;
    private IdentityType identityType;
    private String identityNumber;
    // IDs for form binding
    private Long classRoomId;
    private Long versionId;
    private Long sectionId;

    // Names for display in templates
    private String classRoomName;
    private String versionName;
    private String sectionName;
    private String yearName;

    private List<AddressResponseDto> addresses = new ArrayList<>();
    private List<StudentAcademicRecord> studentAcademicRecords = new ArrayList<>();

}
