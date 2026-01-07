package com.smatik.sms.employee.model.dto.response;

import com.smatik.sms.common.address.dto.AddressResponseDto;
import com.smatik.sms.common.enums.EmployeeType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeResponseDto {

    private Long id;
    private Long EmployId;
    private String Name;
    private Gender gender;
    private LocalDate dob;
    private LocalDate joiningDate;
    private Double salary;
    private EmployeeType employeeType;
    private IdentityType identityType;
    private Long identityNumber;
    private String phoneNumber;
    private String photo;
    private String photoDir;
    private String nid;
    private String nidDir;

    private int serialNo;

    private List<AddressResponseDto> addressResponseDto = new ArrayList<>();
}
