package com.smha.sms.attendance.model.repository;

import com.smha.sms.attendance.model.entity.Attendance;
import com.smha.sms.employee.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // নির্দিষ্ট তারিখের সব হাজিরা খুঁজে বের করার জন্য
    List<Attendance> findAllByDate(LocalDate date);

    boolean existsByDateAndEmployeeId_Id(LocalDate date, Long employeeId);

    // একজন employee এর নির্দিষ্ট দিনে attendance already আছে কিনা
    boolean existsByEmployeeIdAndDate(Employee employee, LocalDate date);
}
