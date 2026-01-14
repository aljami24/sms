package com.smha.sms.employee.model.dto.request;

import com.smha.sms.common.address.dto.AddressRequestDto;
import com.smha.sms.common.enums.EmployeeType;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import com.smha.sms.student.model.dto.request.FileUpload;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeFormDto extends FileUpload {

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
    private List<AddressRequestDto> addressRequestDto = new ArrayList<>();
}
