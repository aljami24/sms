package com.smatik.sms.employee.model.dto.response;

import com.smatik.sms.common.address.dto.AddressResponseDto;
import com.smatik.sms.common.enums.EmployeeType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import com.smatik.sms.student.model.dto.request.FileUpload;
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
    private Boolean active;


    private int serialNo;

    private List<AddressResponseDto> addressResponseDto = new ArrayList<>();
}
