package com.smha.sms.attendance.model.entity;

import com.smha.sms.common.enums.AttendanceStatus;
import com.smha.sms.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AttendanceStatus status;
    @ManyToOne
    private Employee employeeId;

    @Override
    public String toString() {
        return "EmployeeAttendance{" +
                "id=" + id +
                ", status=" + status +
                ", employeeId=" + employeeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeAttendance that = (EmployeeAttendance) o;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, employeeId);
    }
}
