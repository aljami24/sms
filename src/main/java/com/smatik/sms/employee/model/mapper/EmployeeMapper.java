package com.smatik.sms.employee.model.mapper;

import com.smatik.sms.common.address.dto.AddressRequestDto;
import com.smatik.sms.common.address.dto.AddressResponseDto;
import com.smatik.sms.common.address.entity.Address;
import com.smatik.sms.common.enums.AddressType;
import com.smatik.sms.employee.model.dto.request.EmployeeFormDto;
import com.smatik.sms.employee.model.dto.response.EmployeeResponseDto;
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

        // অ্যাড্রেস ম্যাপিং (আপনার দেওয়া লজিক অনুযায়ী)
        if (employeeFormDto.getAddressRequestDto() != null) {
            // ১. ফর্মে যে টাইপগুলো নেই, সেগুলো ডাটাবেস (Entity) থেকে রিমুভ করে দেওয়া
            List<AddressType> incomingTypes = employeeFormDto.getAddressRequestDto()
                    .stream()
                    .map(AddressRequestDto::getAddressType)
                    .toList();

            employee.getAddress().removeIf(address -> !incomingTypes.contains(address.getAddressType()));

            // ২. ফর্মে আসা প্রতিটি অ্যাড্রেস চেক করা
            employeeFormDto.getAddressRequestDto().forEach(dto -> {
                employee.getAddress().stream()
                        .filter(existing -> existing.getAddressType() == dto.getAddressType())
                        .findFirst()
                        .ifPresentOrElse(
                                existingAddress -> {
                                    // আপডেট কেস: নতুন ডাটা আসলে আপডেট হবে, নয়তো আগেরটা থাকবে
                                    if (dto.getDivision() != null) existingAddress.setDivision(dto.getDivision());
                                    if (dto.getDistrict() != null) existingAddress.setDistrict(dto.getDistrict());
                                    if (dto.getPoliceStation() != null) existingAddress.setPoliceStation(dto.getPoliceStation());
                                    if (dto.getVillage() != null && !dto.getVillage().isBlank()) {
                                        existingAddress.setVillage(dto.getVillage());
                                    }
                                },
                                () -> {
                                    // সেভ কেস: নতুন টাইপ হলে নতুন অবজেক্ট তৈরি হবে
                                    Address newAddress = new Address();
                                    newAddress.setAddressType(dto.getAddressType());
                                    newAddress.setDivision(dto.getDivision());
                                    newAddress.setDistrict(dto.getDistrict());
                                    newAddress.setPoliceStation(dto.getPoliceStation());
                                    newAddress.setVillage(dto.getVillage());

                                    newAddress.setEmployee(employee);
                                    newAddress.setStudent(null);
                                    employee.getAddress().add(newAddress);
                                }
                        );
            });
        }

        return employee;
    }






    public static Employee mapFileDir (Employee employee, EmployeeFormDto employeeFormDto){

        if(employeeFormDto.getPhotoDir() != null){
            employee.setPhotoDir(employeeFormDto.getPhotoDir());
        }
        if (employeeFormDto.getNidDir() != null){
            employee.setNidDir(employeeFormDto.getNidDir());
        }
        return employee;
    }






    public static EmployeeResponseDto employeeEntityToResponse(Employee employee) {

        EmployeeResponseDto responseDto = new EmployeeResponseDto();

        responseDto.setId(employee.getId());
        responseDto.setEmployeeId(employee.getEmployeeId());
        responseDto.setName(employee.getName());
        responseDto.setGender(employee.getGender());
        responseDto.setDob(employee.getDob());
        responseDto.setJoiningDate(employee.getJoiningDate());
        responseDto.setSalary(employee.getSalary());
        responseDto.setEmployeeType(employee.getEmployeeType());
        responseDto.setIdentityType(employee.getIdentityType());
        responseDto.setIdentityNumber(employee.getIdentityNumber());
        responseDto.setPhoneNumber(employee.getPhoneNumber());
        responseDto.setStatus(employee.getStatus());
        responseDto.setPhotoDir(employee.getPhotoDir());
        responseDto.setNidDir(employee.getNidDir());

        List<AddressResponseDto> addressList = new ArrayList<>();

        if (employee.getAddress() != null) {
            for (Address address : employee.getAddress()) {

                AddressResponseDto addressDto = new AddressResponseDto();
                addressDto.setAddressType(address.getAddressType());
                if (address.getDivision() != null) {
                    addressDto.setDivision(address.getDivision());
                }
                if (address.getDistrict() != null) {
                    addressDto.setDistrict(address.getDistrict());
                }
                if (address.getPoliceStation() != null) {
                    addressDto.setPoliceStation(address.getPoliceStation());
                }
                addressDto.setVillage(address.getVillage());

                addressList.add(addressDto);
            }
        }

        responseDto.setAddressResponseDto(addressList);

        return responseDto;
    }

}
