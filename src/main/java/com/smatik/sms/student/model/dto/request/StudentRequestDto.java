package com.smatik.sms.student.model.dto.request;

import com.smatik.sms.academic.model.entity.ClassroomVersionSection;
import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
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
    private Long identityNumber;
    private Long classRoomId;
    private Long versionId;
    private Long sectionId;
    private String photoDir;
    private String nidDir;
    private ClassroomVersionSection classroomVersionSectionsId;
    private List<AddressRequestDto> addresses = new ArrayList<>();
}
