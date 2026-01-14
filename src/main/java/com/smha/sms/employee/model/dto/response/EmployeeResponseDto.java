package com.smha.sms.employee.model.dto.response;

import com.smha.sms.common.address.dto.AddressResponseDto;
import com.smha.sms.common.enums.EmployeeType;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import com.smha.sms.employee.model.enums.EmployeeStatus;
import com.smha.sms.student.model.dto.request.FileUpload;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeResponseDto extends FileUpload {

    private Long employeeId;
    private String name;
    private Gender gender;
    private LocalDate dob;
    private LocalDate joiningDate;
    private Double salary;
    private EmployeeType employeeType;
    private IdentityType identityType;
    private String identityNumber;
    private String phoneNumber;
    private EmployeeStatus status;
    private Boolean active;


    private int serialNo;

    private List<AddressResponseDto> addressResponseDto = new ArrayList<>();
}
