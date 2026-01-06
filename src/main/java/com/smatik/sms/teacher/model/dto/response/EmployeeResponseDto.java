package com.smatik.sms.teacher.model.dto.response;

import com.smatik.sms.common.enums.EmployType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import com.smatik.sms.student.model.entity.Address;
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
    private EmployType employType;
    private IdentityType identityType;
    private Long identityNumber;
    private String phoneNumber;
    private String photo;
    private String photoDir;
    private String nid;
    private String nidDir;

    private int serialNo;

    private List<Address> address = new ArrayList<>();
}
