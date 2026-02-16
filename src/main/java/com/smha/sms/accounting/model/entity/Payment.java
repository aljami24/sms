package com.smha.sms.accounting.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.enums.PaymentMethod;
import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.student.model.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    /*@ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;*/

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    /*@ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "cvs_id")
    private ClassroomVersionSection classroomVersionSection;*/

    /*@ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "year_id")
    private Year yearId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "invoice_id")
    private Invoice invoiceId;*/

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated (EnumType.STRING)
    private PaymentStatus status;

    private BigDecimal amount;

    private String transactionNo;

    private LocalDateTime transactionDate;
}
