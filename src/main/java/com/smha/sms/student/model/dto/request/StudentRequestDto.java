package com.smha.sms.student.model.dto.request;

import com.smha.sms.common.address.dto.AddressRequestDto;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentRequestDto
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Getter
@Setter
public class StudentRequestDto extends FileUpload {
    private Long id;
    private String name;
    private String fatherName;
    private String motherName;
    private Gender gender;
    private LocalDate dob;
    private IdentityType identityType;
    private String identityNumber;
    private Long classRoomId;
    private Long versionId;
    private Long sectionId;
    private Long yearId;
    private String photoDir;
    private String nidDir;
    private List<AddressRequestDto> addresses = new ArrayList<>();
}
