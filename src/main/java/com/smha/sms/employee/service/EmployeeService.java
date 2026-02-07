package com.smha.sms.employee.service;

import com.smha.sms.common.util.Helper;
import com.smha.sms.employee.model.dto.EmployeeFilter;
import com.smha.sms.employee.model.dto.request.EmployeeFormDto;
import com.smha.sms.employee.model.dto.response.EmployeeResponseDto;
import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.model.enums.EmployeeStatus;
import com.smha.sms.employee.model.mapper.EmployeeMapper;
import com.smha.sms.employee.model.repository.EmployeeRepository;
import com.smha.sms.employee.model.spacification.EmployeeSpecification;
import com.smha.sms.employee.service.notification.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * StudentService
 * Author: afzal
 * Created On: 2026-01-05
 * Module: employee Management
 */
@Service
public class EmployeeService {

    @Value("${file.upload-directory}")
    private String uploadDir;

    private final EmployeeRepository employeeRepository;
    private final NotificationService emailService;
    private final NotificationService smsService;

    public EmployeeService (
            EmployeeRepository employeeRepository,
            @Qualifier("emailService") NotificationService emailService,
            @Qualifier("smsService") NotificationService smsService
    ){
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.smsService = smsService;
    }


    // Create----------------------------------------------------------------------------------------------------------------------------------
    @Transactional
    public void saveEmployee(EmployeeFormDto employeeFormDto) {
        Employee employee = new Employee();
        EmployeeMapper.employeeFormToEntity(employee, employeeFormDto);
        employee.setStatus(EmployeeStatus.APPOINTED);
        employee.setActive(false);

        employeeRepository.save(employee);
        employeeFormDto.setId(employee.getId());
        Helper.employeeFilesUpload(uploadDir, employeeFormDto);
        EmployeeMapper.mapFileDir(employee, employeeFormDto);

        employeeRepository.save(employee);

        // notification
        String message = "Employee created successfully: " + employee.getName();

        smsService.send(message, employee);
        emailService.send(message, employee);


    }

    // Read----------------------------------------------------------------------------------------------------------------------------------

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

    // Filter -------------------------------------------------------------------------------
    public Page<EmployeeResponseDto> getAllFilterEmployee(EmployeeFilter filter, int page, int pageSize, String sortField, String sortOrder
    ) {

        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(direction, sortField));

        AtomicInteger serialNo = new AtomicInteger(page * pageSize + 1);
        Page<Employee> employeePage = employeeRepository.findAll(EmployeeSpecification.filter(filter), pageRequest);

        return employeePage.map(employee -> {
            EmployeeResponseDto employeeResponseDto = EmployeeMapper.employeeEntityToResponse(employee);
            employeeResponseDto.setSerialNo(serialNo.getAndIncrement());
            return employeeResponseDto;
        });
    }


    // ================= Get By ID =================
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        return EmployeeMapper.employeeEntityToResponse(employee);
    }

    // ================= EmployeeActivateStatus By ID =================
    @Transactional
    public Employee activateEmployee(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        if (employee.getStatus() == EmployeeStatus.ACTIVATE) {
            throw new RuntimeException("Employee already Activate");
        }

        employee.setStatus(EmployeeStatus.ACTIVATE);
        employee.setActive(true);

        return employeeRepository.save(employee);
    }

    // ================= EmployeeDeactivateStatus By ID =================
    public Employee deactivateEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        if (employee.getStatus() != EmployeeStatus.ACTIVATE) {
            throw new IllegalStateException("Only joined employees can be deactivated");
        }

        employee.setStatus(EmployeeStatus.DEACTIVATE);
        employee.setActive(false);
        return employeeRepository.save(employee);
    }

    // Update----------------------------------------------------------------------------------------------------------------------------------

    @Transactional
    public Employee updateExistingEmployee(EmployeeFormDto employeeFormDto) {

        Employee existingTeacher = employeeRepository.findById(employeeFormDto.getId()).orElseThrow();
        EmployeeMapper.employeeFormToEntity(existingTeacher, employeeFormDto);
        Helper.employeeFilesUpload(uploadDir, employeeFormDto);
        EmployeeMapper.mapFileDir(existingTeacher, employeeFormDto);
        return employeeRepository.save(existingTeacher);
    }

    public Optional<Employee> editById(Long id) {
        return employeeRepository.findById(id);
    }

    // Delete-------------------------------------------------------------------------------

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee not found with id: " + id));

        // Delete employee when employee status is appointed
        if (employee.getStatus() != EmployeeStatus.APPOINTED) {
            throw new IllegalStateException("Activate or Deactivate employee cannot be deleted");
        }
        // Delete all employee files
        Helper.deleteEmployeeAllFiles(uploadDir, employee.getId());

        // Delete employee
        employeeRepository.delete(employee);
    }

}
