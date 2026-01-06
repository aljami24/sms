package com.smatik.sms.teacher.service;

import com.smatik.sms.teacher.model.dto.request.EmployeeFormDto;
import com.smatik.sms.teacher.model.dto.response.EmployeeResponseDto;
import com.smatik.sms.teacher.model.entity.Employee;
import com.smatik.sms.teacher.model.mapper.EmployeeMapper;
import com.smatik.sms.teacher.model.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository empolyeeRepository;


    // Create-------------------------------------------------------------------------------
    public void saveEmployee (EmployeeFormDto employeeFormDto) {
        Employee employee = EmployeeMapper.employeeFormToEntity(employeeFormDto);

        empolyeeRepository.save(employee);

    }
    // Update-------------------------------------------------------------------------------

    @Transactional
    public Employee updateEmployee (Long id, EmployeeFormDto employeeFormDto) {
        Employee existingEmployee = empolyeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        existingEmployee.setEmployId(employeeFormDto.getEmployId());
        existingEmployee.setName(employeeFormDto.getName());
        existingEmployee.setGender(employeeFormDto.getGender());
        existingEmployee.setDob(employeeFormDto.getDob());
        existingEmployee.setJoiningDate(employeeFormDto.getJoiningDate());
        existingEmployee.setSalary(employeeFormDto.getSalary());
        existingEmployee.setEmployType(employeeFormDto.getEmployType());
        existingEmployee.setIdentityType(employeeFormDto.getIdentityType());
        existingEmployee.setIdentityNumber(employeeFormDto.getIdentityNumber());
        existingEmployee.setPhoneNumber(employeeFormDto.getPhoneNumber());
        existingEmployee.setAddress(employeeFormDto.getAddress());

        Employee updateEmployee = empolyeeRepository.save(existingEmployee);

        return updateEmployee;

    }
    // Read---------------------------------------------------------------------------------

    public List<EmployeeResponseDto> getAllEmployee (int page, int pageSize, String sortField,String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder),sortField);
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);

        int serialNo = page * pageSize + 1;

        List<EmployeeResponseDto> employeeResponseDtos = empolyeeRepository.findAll(pageRequest)
                .stream()
                .map(employee -> {
                    EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
                    employeeResponseDto.setEmployId(employee.getEmployId());
                    employeeResponseDto.setName(employee.getName());
                    employeeResponseDto.setGender(employee.getGender());
                    employeeResponseDto.setDob(employee.getDob());
                    employeeResponseDto.setJoiningDate(employee.getJoiningDate());
                    employeeResponseDto.setSalary(employee.getSalary());
                    employeeResponseDto.setEmployType(employee.getEmployType());
                    employeeResponseDto.setIdentityType(employee.getIdentityType());
                    employeeResponseDto.setIdentityNumber(employee.getIdentityNumber());
                    employeeResponseDto.setPhoneNumber(employee.getPhoneNumber());
                    employeeResponseDto.setAddress(employee.getAddress());

                    return employeeResponseDto;

                })
                .toList();

        for (int i = 0; i < employeeResponseDtos.size(); i++) {
            employeeResponseDtos.get(i).setSerialNo(serialNo + i);
        }

        return employeeResponseDtos;
    }


    // Delete-------------------------------------------------------------------------------
}
