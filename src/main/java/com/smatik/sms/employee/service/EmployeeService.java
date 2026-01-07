package com.smatik.sms.employee.service;

import com.smatik.sms.common.constants.Constants;
import com.smatik.sms.common.util.Helper;
import com.smatik.sms.employee.model.dto.request.EmployeeFormDto;
import com.smatik.sms.employee.model.entity.Employee;
import com.smatik.sms.employee.model.mapper.EmployeeMapper;
import com.smatik.sms.employee.model.repository.EmployeeRepository;
import org.codehaus.groovy.runtime.metaclass.MetaMethodIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * StudentService
 * Author: afzal
 * Created On: 2026-01-05
 * Module: employee Management
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empolyeeRepository;

    @Value("${file.upload-directory}")
    private String uploadDir;


    // Create-------------------------------------------------------------------------------
    public void saveEmployee (EmployeeFormDto employeeFormDto) {
        Employee employee = new Employee();
        EmployeeMapper.employeeFormToEntity(employee, employeeFormDto);

        empolyeeRepository.save(employee);
        employeeFormDto.setId(employee.getId());
        Helper.employeeFilesUpload(uploadDir, employeeFormDto);

        employee.setPhotoDir(employeeFormDto.getPhotoDir());
        employee.setNidDir(employeeFormDto.getNidDir());
        empolyeeRepository.save(employee);

    }
    // Update-------------------------------------------------------------------------------

//    @Transactional
//    public Employee updateEmployee (Long id, EmployeeFormDto employeeFormDto) {
//        Employee existingEmployee = empolyeeRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
//
//        existingEmployee.setEmployId(employeeFormDto.getEmployId());
//        existingEmployee.setName(employeeFormDto.getName());
//        existingEmployee.setGender(employeeFormDto.getGender());
//        existingEmployee.setDob(employeeFormDto.getDob());
//        existingEmployee.setJoiningDate(employeeFormDto.getJoiningDate());
//        existingEmployee.setSalary(employeeFormDto.getSalary());
//        existingEmployee.setEmployType(employeeFormDto.getEmployType());
//        existingEmployee.setIdentityType(employeeFormDto.getIdentityType());
//        existingEmployee.setIdentityNumber(employeeFormDto.getIdentityNumber());
//        existingEmployee.setPhoneNumber(employeeFormDto.getPhoneNumber());
//        existingEmployee.setAddress(employeeFormDto.getAddress());
//
//        Employee updateEmployee = empolyeeRepository.save(existingEmployee);
//
//        return updateEmployee;
//
//    }
    // Read---------------------------------------------------------------------------------

//    public List<EmployeeResponseDto> getAllEmployee (int page, int pageSize, String sortField,String sortOrder) {
//        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder),sortField);
//        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
//
//        int serialNo = page * pageSize + 1;
//
//        List<EmployeeResponseDto> employeeResponseDtos = empolyeeRepository.findAll(pageRequest)
//                .stream()
//                .map(employee -> {
//                    EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
//                    employeeResponseDto.setEmployId(employee.getEmployId());
//                    employeeResponseDto.setName(employee.getName());
//                    employeeResponseDto.setGender(employee.getGender());
//                    employeeResponseDto.setDob(employee.getDob());
//                    employeeResponseDto.setJoiningDate(employee.getJoiningDate());
//                    employeeResponseDto.setSalary(employee.getSalary());
//                    employeeResponseDto.setEmployType(employee.getEmployType());
//                    employeeResponseDto.setIdentityType(employee.getIdentityType());
//                    employeeResponseDto.setIdentityNumber(employee.getIdentityNumber());
//                    employeeResponseDto.setPhoneNumber(employee.getPhoneNumber());
//                    employeeResponseDto.setAddress(employee.getAddress());
//
//                    return employeeResponseDto;
//
//                })
//                .toList();
//
//        for (int i = 0; i < employeeResponseDtos.size(); i++) {
//            employeeResponseDtos.get(i).setSerialNo(serialNo + i);
//        }
//
//        return employeeResponseDtos;
//    }


    // Delete-------------------------------------------------------------------------------
}
