package com.smatik.sms.employee.model.dto.request;

import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.enums.EmployType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeFormDto {

    private Long id;
    private Long EmployId;
    private String Name;
    private Gender gender;
    private LocalDate dob;
    private LocalDate joiningDate;
    private Double salary;
    private EmployType employType;
    private IdentityType identityType;
    private Long identityNumber;
    private String phoneNumber;
    private List<AddressRequestDto> addressRequestDto = new ArrayList<>();
}
