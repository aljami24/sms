package com.smatik.sms.common.address.dto;

import com.smatik.sms.common.address.entity.District;
import com.smatik.sms.common.address.entity.Division;
import com.smatik.sms.common.address.entity.PoliceStation;
import com.smatik.sms.common.enums.AddressType;
import com.smatik.sms.employee.model.entity.Employee;
import com.smatik.sms.student.model.entity.Student;
import lombok.Getter;
import lombok.Setter;

/**
 * AddressRequestDto
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Getter
@Setter
public class AddressRequestDto {
    private Long id;
    private String village;
    private AddressType addressType;
    private Division division;
    private District district;
    private PoliceStation policeStation;
    private Employee employee;
    private Student student;
}
