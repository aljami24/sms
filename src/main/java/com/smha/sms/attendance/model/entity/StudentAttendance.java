package com.smha.sms.attendance.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.common.enums.AttendanceStatus;
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
public class StudentAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private AttendanceStatus status;
    @ManyToOne
    private Student studentId;
    @ManyToOne
    private ClassroomVersionSection classStructureId;
    @ManyToOne
    private Employee teacherId;

    @Override
    public String toString() {
        return "StudentAttendance{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                ", studentId=" + studentId +
                ", classStructureId=" + classStructureId +
                ", teacherId=" + teacherId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentAttendance that = (StudentAttendance) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && status == that.status && Objects.equals(studentId, that.studentId) && Objects.equals(classStructureId, that.classStructureId) && Objects.equals(teacherId, that.teacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, studentId, classStructureId, teacherId);
    }
}
