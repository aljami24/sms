package com.smatik.sms.employee.service;

import com.smatik.sms.common.util.Helper;
import com.smatik.sms.employee.model.dto.request.EmployeeFormDto;
import com.smatik.sms.employee.model.dto.response.EmployeeResponseDto;
import com.smatik.sms.employee.model.entity.Employee;
import com.smatik.sms.employee.model.mapper.EmployeeMapper;
import com.smatik.sms.employee.model.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * EmployeeService
 * Author: afzal
 * Created On: 2026-01-05
 * Module: employee Management
 */

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Value("${file.upload-directory}")
    private String uploadDir;


    // Create-------------------------------------------------------------------------------
    @Transactional
    public void saveEmployee(EmployeeFormDto employeeFormDto) {
        Employee employee = new Employee();
        EmployeeMapper.employeeFormToEntity(employee, employeeFormDto);

        employeeRepository.save(employee);
        employeeFormDto.setId(employee.getId());
        Helper.employeeFilesUpload(uploadDir, employeeFormDto);
        EmployeeMapper.mapFileDir(employee, employeeFormDto);

        employeeRepository.save(employee);

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

    public Page<EmployeeResponseDto> getAllEmployee(int page, int pageSize, String sortField, String sortOrder
    ) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(direction, sortField));

        AtomicInteger serialNo = new AtomicInteger(page * pageSize + 1);

        Page<Employee> employeePage = employeeRepository.findAll(pageRequest);

        return employeePage.map(employee -> {
            EmployeeResponseDto employeeResponseDto = EmployeeMapper.employeeEntityToResponse(employee);
            employeeResponseDto.setSerialNo(serialNo.getAndIncrement());
            return employeeResponseDto;
        });
    }

    @Transactional
    public Employee updateExistingEmployee(EmployeeFormDto employeeFormDto) {

        Employee existingTeacher = employeeRepository.findById(employeeFormDto.getId()).orElseThrow();

        EmployeeMapper.employeeFormToEntity(existingTeacher, employeeFormDto);

        Helper.employeeFilesUpload(uploadDir, employeeFormDto);

        EmployeeMapper.mapFileDir(existingTeacher, employeeFormDto);
        return employeeRepository.save(existingTeacher);
    }

    // ================= Get By ID =================
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        return EmployeeMapper.employeeEntityToResponse(employee);
    }

    public Optional<Employee> editById(Long id) {
        return employeeRepository.findById(id);
    }

    // Delete-------------------------------------------------------------------------------


    // ================= 1. General Delete =================
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        // Delete all employee files
        Helper.deleteEmployeeAllFiles(uploadDir, employee.getId());

        // Delete employee
        employeeRepository.delete(employee);
    }

    // ================= 2. Active Check Delete =================
    public void deleteEmployeeIfInactive(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        if (Boolean.TRUE.equals(employee.getActive())) {
            throw new IllegalStateException("Cannot delete employee because active is true");
        }

        // Delete files
        Helper.deleteEmployeeAllFiles(uploadDir, employee.getId());

        // Delete employee
        employeeRepository.delete(employee);
    }
}
