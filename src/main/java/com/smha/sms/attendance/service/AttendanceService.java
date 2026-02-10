package com.smha.sms.attendance.service;

import com.smha.sms.attendance.model.entity.Attendance;
import com.smha.sms.attendance.model.repository.AttendanceRepository;
import com.smha.sms.common.enums.AttendanceStatus;
import com.smha.sms.common.enums.MonthName;
import com.smha.sms.employee.model.dto.EmployeeFilter;
import com.smha.sms.employee.model.dto.response.EmployeeResponseDto;
import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.employee.model.enums.EmployeeStatus;
import com.smha.sms.employee.model.mapper.EmployeeMapper;
import com.smha.sms.employee.model.repository.EmployeeRepository;
import com.smha.sms.employee.model.spacification.EmployeeSpecification;
import com.smha.sms.user.model.entity.User;
import com.smha.sms.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;


    public List<EmployeeResponseDto> getAllActiveEmployeesForAttendance(EmployeeFilter filter) {
        LocalDate today = LocalDate.now();

        // ১. শুধুমাত্র ACTIVATE স্ট্যাটাস সেট করা
        filter.setStatus(EmployeeStatus.ACTIVATE);

        // ২. স্পেসিফিকেশন অনুযায়ী সকল একটিভ এমপ্লয়ীদের আনা
        List<Employee> allActiveEmployees = employeeRepository.findAll(EmployeeSpecification.filter(filter));

        // ৩. আজকের দিনে যাদের হাজিরা অলরেডি নেওয়া হয়েছে তাদের আইডিগুলো সংগ্রহ করা
        // (নোট: attendanceRepository তে এই ফাইন্ডার মেথডটি থাকতে হবে)
        List<Long> alreadyMarkedEmployeeIds = attendanceRepository.findAllByDate(today)
                .stream()
                .filter(a -> a.getEmployeeId() != null)
                .map(a -> a.getEmployeeId().getId())
                .toList();

        // ৪. যাদের হাজিরা নেওয়া হয়নি শুধু তাদের ফিল্টার করে রিটার্ন করা
        return allActiveEmployees.stream()
                .filter(emp -> !alreadyMarkedEmployeeIds.contains(emp.getId()))
                .map(EmployeeMapper::employeeEntityToResponse)
                .toList();
    }

    @Transactional
    public void processEmployeeAttendance(List<Long> allDisplayedIds, Map<String, String> allParams) {
        LocalDate today = LocalDate.now();
        MonthName currentMonth = MonthName.valueOf(today.getMonth().name());

        // ১. বর্তমানে লগইন করা ইউজারের ইউজারনেম বের করা
        String currentUsername = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        // ২. ডাটাবেস থেকে লগইন করা User অবজেক্টটি নিয়ে আসা
        User loggedInUser = userRepository.findByUsername(currentUsername).orElse(null);

        for (Long empId : allDisplayedIds) {

            // ১. আজকের তারিখে এই এমপ্লয়ীর হাজিরা অলরেডি আছে কি না চেক (নিরাপত্তার জন্য)
            boolean exists = attendanceRepository.existsByDateAndEmployeeId_Id(today, empId);

            if (!exists) {
                Employee emp = employeeRepository.findById(empId).orElse(null);
                if (emp != null) {
                    Attendance attendance = new Attendance();
                    attendance.setDate(today);
                    attendance.setEmployeeId(emp);
                    attendance.setStudentId(null);
                    attendance.setMonthName(currentMonth);
                    attendance.setMarkedBy(loggedInUser);

                    // ২. চেক করা: সুইচ অন আছে কি না (প্যারামিটারে status_ID আছে কিনা)
                    if (allParams.containsKey("status_" + empId)) {
                        attendance.setStatus(AttendanceStatus.PRESENT);
                    } else {
                        // ৩. যদি প্যারামিটারে না থাকে (আনচেকড), তবে সে ABSENT
                        attendance.setStatus(AttendanceStatus.ABSENT);
                    }

                    attendanceRepository.save(attendance);
                }
            }
        }
    }

    @Transactional
    public void toggleAttendanceStatus(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        // স্ট্যাটাস যদি PRESENT থাকে তবে ABSENT করবে, আর ABSENT থাকলে PRESENT করবে
        if (attendance.getStatus() == AttendanceStatus.PRESENT) {
            attendance.setStatus(AttendanceStatus.ABSENT);
        } else {
            attendance.setStatus(AttendanceStatus.PRESENT);
        }

        attendanceRepository.save(attendance);
    }
}