package com.smha.sms.accounting.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.enums.PaymentMethod;
import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.student.model.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Month;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student studentId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "cvs_id")
    private ClassroomVersionSection cvsId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "year_id")
    private Year yearId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "invoice_id")
    private Invoice invoiceId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated (EnumType.STRING)
    private PaymentStatus status;

    private Double amount;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "payment_type_id")
    private PaymentType paymentType;

    private String transactionId;

    @Enumerated (EnumType.STRING)
    private Month month;
}
