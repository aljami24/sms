package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.accounting.model.enums.InvoicePaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByStudentIdAndYearIdAndClassroomVersionSectionIdAndInvoicePaymentStatus(
            Long studentId,
            Long yearId,
            Long classroomVersionSectionId,
            InvoicePaymentStatus status
    );

    List<Invoice> findByStudentIdAndYearIdAndClassroomVersionSectionId(
            Long studentId,
            Long yearId,
            Long classroomVersionSectionId
    );

    List<Invoice> findByInvoiceNoContainingIgnoreCase(String invoiceNo);
}