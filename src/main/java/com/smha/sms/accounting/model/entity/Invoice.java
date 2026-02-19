package com.smha.sms.accounting.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.accounting.model.enums.PaymentMethod;
import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.student.model.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity {

    @ManyToOne (fetch =  FetchType.LAZY)
    @JoinColumn (name = "student_id")
    private Student student;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "cvs_id")
    private ClassroomVersionSection classroomVersionSection;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "year_id")
    private Year year;

    private String invoiceNo;

    private LocalDateTime invoiceDate;

    @Enumerated (EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated (EnumType.STRING)
    private PaymentStatus status;

    @Enumerated (EnumType.STRING)
    private FeeType feeType;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private BigDecimal dueAmount;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "payment_type_id")
    private PaymentType paymentType;

    private String transactionId;

    @Enumerated (EnumType.STRING)
    private Month month;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> invoiceItems = new ArrayList<>();
}
