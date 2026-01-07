package com.smatik.sms.employee.model.mapper;

import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.address.entity.Address;
import com.smatik.sms.employee.model.dto.request.EmployeeFormDto;
import com.smatik.sms.employee.model.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EmployeeMapper {

    public static Employee employeeFormToEntity(Employee employee, EmployeeFormDto employeeFormDto) {
        employee.setEmployeeId(employeeFormDto.getEmployeeId());
        employee.setName(employeeFormDto.getName());
        employee.setGender(employeeFormDto.getGender());
        employee.setDob(employeeFormDto.getDob());
        employee.setJoiningDate(employeeFormDto.getJoiningDate());
        employee.setSalary(employeeFormDto.getSalary());
        employee.setEmployeeType(employeeFormDto.getEmployeeType());
        employee.setIdentityType(employeeFormDto.getIdentityType());
        employee.setIdentityNumber(employeeFormDto.getIdentityNumber());
        employee.setPhoneNumber(employeeFormDto.getPhoneNumber());
        employee.setPhotoDir(employeeFormDto.getPhotoDir());
        employee.setNidDir(employeeFormDto.getNidDir());

        List<Address> addressList = new ArrayList<>();

        if (employeeFormDto.getAddressRequestDto() != null) {
            for (AddressRequestDto addressRequestDto : employeeFormDto.getAddressRequestDto()) {

                Address address = new Address();
                address.setAddressType(addressRequestDto.getAddressType());
                address.setDivision(addressRequestDto.getDivision());
                address.setDistrict(addressRequestDto.getDistrict());
                address.setPoliceStation(addressRequestDto.getPoliceStation());
                address.setVillage(addressRequestDto.getVillage());

                // 🔥 Important (relation set)
                address.setEmployee(employee);
                address.setStudent(null);

                addressList.add(address);
            }
        }

        employee.setAddress(addressList);

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
