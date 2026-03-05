package com.smha.sms.accounting.model.entity;

import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.student.model.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRecord extends BaseEntity {

    private LocalDate paymentDate;
    private Double paymentAmount;

    @Enumerated(EnumType.STRING)
    private Month month;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;


    @Override
    public String toString() {
        return "PaymentRecord{" +
                "paymentDate=" + paymentDate +
                ", amount=" + paymentAmount +
                ", month=" + month +
                ", year=" + year +
                ", status=" + status +
                ", paymentType=" + paymentType +
                ", student=" + student +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRecord that = (PaymentRecord) o;
        return Objects.equals(paymentDate, that.paymentDate) && Objects.equals(paymentAmount, that.paymentAmount) && month == that.month && Objects.equals(year, that.year) && status == that.status && Objects.equals(paymentType, that.paymentType) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentDate, paymentAmount, month, year, status, paymentType, student);
    }
}
