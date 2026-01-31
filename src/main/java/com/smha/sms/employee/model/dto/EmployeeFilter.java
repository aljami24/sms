package com.smha.sms.employee.model.dto;

import com.smha.sms.common.enums.EmployeeType;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.employee.model.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeFilter {

    private Long employeeId;
    private String name;
    private Gender gender;
//    private Double salary;
    private EmployeeType employeeType;
    private String identityNumber;
    private String phoneNumber;
    private EmployeeStatus status;
    private Long divisionId;
    private Long districtId;
    private Long policeStationId;
}
