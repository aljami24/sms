package com.smatik.sms.student.model.dto.response;

import com.smatik.sms.common.address.dto.AddressResponseDto;
import com.smatik.sms.common.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * StudentResponseDto
 * Author: jami
 * Created On: 2026-01-05
 * Module: Student Management
 */

@Getter
@Setter
public class StudentResponseDto {

    private String name;
    private String fatherName;
    private String motherName;
    private Gender gender;
    private LocalDate dob;
    private List<AddressResponseDto> addresses;
}
