package com.smha.sms.attendance.model.entity;

import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.common.enums.AttendanceStatus;
import com.smha.sms.common.enums.MonthName;
import com.smha.sms.employee.model.entity.Employee;
import com.smha.sms.student.model.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance extends BaseEntity {


    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    @Enumerated(EnumType.STRING)
    private MonthName monthName;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    @ManyToOne
    @JoinColumn (name = "marked_by")
    private Employee markedBy;

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + getId() +
                ", date=" + date +
                ", status=" + status +
                ", monthName=" + monthName +
                ", studentId=" + studentId +
                ", employeeId=" + employeeId +
                ", markedBy=" + markedBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(date, that.date) && status == that.status && monthName == that.monthName && Objects.equals(studentId, that.studentId) && Objects.equals(employeeId, that.employeeId) && Objects.equals(markedBy, that.markedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), date, status, monthName, studentId, employeeId, markedBy);
    }
}
