package com.smatik.sms.employee.model.mapper;

import com.smatik.sms.employee.model.dto.request.EmployeeFormDto;
import com.smatik.sms.employee.model.entity.Employee;
import org.springframework.stereotype.Component;


@Component
public class EmployeeMapper {

    public static Employee employeeFormToEntity(EmployeeFormDto employeeFormDto) {
        Employee employee = new Employee();
        employee.setEmployId(employeeFormDto.getEmployId());
        employee.setName(employeeFormDto.getName());
        employee.setGender(employeeFormDto.getGender());
        employee.setDob(employeeFormDto.getDob());
        employee.setJoiningDate(employeeFormDto.getJoiningDate());
        employee.setSalary(employeeFormDto.getSalary());
        employee.setEmployeeType(employeeFormDto.getEmployeeType());
        employee.setIdentityType(employeeFormDto.getIdentityType());
        employee.setIdentityNumber(employeeFormDto.getIdentityNumber());
        employee.setPhoneNumber(employeeFormDto.getPhoneNumber());
//        employee.setAddress(employeeFormDto.getAddress());

        return employee;
    }


//    public EmployeeResponseDto entityToEmployeeResponseDto(Employee employee) {
//        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
//        employeeResponseDto.setEmployId(employee.getEmployId());
//        employeeResponseDto.setName(employee.getName());
//        employeeResponseDto.setGender(employee.getGender());
//        employeeResponseDto.setDob(employee.getDob());
//        employeeResponseDto.setJoiningDate(employee.getJoiningDate());
//        employeeResponseDto.setSalary(employee.getSalary());
//        employeeResponseDto.setEmployType(employee.getEmployType());
//        employeeResponseDto.setIdentityType(employee.getIdentityType());
//        employeeResponseDto.setIdentityNumber(employee.getIdentityNumber());
//        employeeResponseDto.setPhoneNumber(employee.getPhoneNumber());
//        employeeResponseDto.setAddress(employee.getAddress());
//
//        return employeeResponseDto;
//    }

}
