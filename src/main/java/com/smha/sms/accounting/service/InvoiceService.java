package com.smha.sms.accounting.service;

import com.smha.sms.academic.model.repository.ClassroomVersionSectionRepository;
import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.accounting.model.dto.request.InvoiceGenerateRequest;
import com.smha.sms.accounting.model.dto.request.InvoiceItemRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.InvoiceItem;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.accounting.model.enums.InvoicePaymentStatus;
import com.smha.sms.accounting.model.repository.InvoiceRepository;
import com.smha.sms.common.util.Helper;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.entity.StudentAcademicRecord;
import com.smha.sms.student.model.repository.StudentRepository;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import com.smha.sms.systemConfiguration.fee.model.repository.FeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClassroomVersionSectionRepository classroomVersionSectionRepository;
    private final YearRepository yearRepository;
    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;

    // Student Fee Details
    public Map<String, Object> getStudentFeeDetails(Long studentId, Long yearId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentAcademicRecord record = student.getStudentAcademicRecords()
                .stream()
                .filter(r -> r.getYear().getId().equals(yearId))
                .findFirst()
                .orElse(null);

        List<Fee> fees = new ArrayList<>();
        if (record != null) {
            fees = feeRepository.findAllByClassroomVersionSectionIdAndYearIdId(
                    record.getClassroomVersionSection().getId(),
                    yearId
            );
        }

        Map<String, Object> map = new HashMap<>();
        map.put("student", student);
        map.put("record", record);
        map.put("fees", fees);
        return map;
    }

    // Save Invoice
    @Transactional
    public Invoice saveInvoice(InvoiceGenerateRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("No invoice items received.");
        }

        Invoice invoice = new Invoice();
        invoice.setStudent(studentRepository.getReferenceById(request.getStudentId()));
        invoice.setYear(yearRepository.getReferenceById(request.getYearId()));
        invoice.setClassroomVersionSection(
                classroomVersionSectionRepository.getReferenceById(request.getClassroomVersionSectionId())
        );

        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceNo(Helper.generateInvoiceNumber());

        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setTotalAmount(BigDecimal.ZERO);
        invoice.setDueAmount(BigDecimal.ZERO);
        invoice.setInvoicePaymentStatus(InvoicePaymentStatus.UNPAID);

        List<InvoiceItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (InvoiceItemRequest itemReq : request.getItems()) {

            if (itemReq.getFeeType() == null || itemReq.getPerFeeAmount() == null)
                continue;

            // Tuition Fee with multiple months
            if (itemReq.getFeeType() == FeeType.TUITION_FEE && itemReq.getMonths() != null) {
                for (Month month : itemReq.getMonths()) {
                    InvoiceItem item = new InvoiceItem();
                    item.setInvoice(invoice);
                    item.setFeeType(FeeType.TUITION_FEE);
                    item.setMonth(month);
                    item.setPerFeeAmount(itemReq.getPerFeeAmount());
                    item.setItemTotal(itemReq.getPerFeeAmount());
                    item.setRemarks("Tuition for " + month.name());
                    items.add(item);
                    totalAmount = totalAmount.add(item.getItemTotal());
                }
            } else if (itemReq.isSelected()) {
                InvoiceItem item = new InvoiceItem();
                item.setInvoice(invoice);
                item.setFeeType(itemReq.getFeeType());
                item.setPerFeeAmount(itemReq.getPerFeeAmount());
                item.setItemTotal(itemReq.getPerFeeAmount());
                item.setRemarks(itemReq.getRemarks());
                item.setMonth(null);

                items.add(item);
                totalAmount = totalAmount.add(item.getItemTotal());
            }
        }

        invoice.setInvoiceItems(items);
        invoice.setTotalAmount(totalAmount);
        invoice.setDueAmount(totalAmount);

        return invoiceRepository.save(invoice);
    }

    // Get Invoice By ID
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    // Check if UNPAID invoice exists
    public Optional<Invoice> findUnpaidInvoice(Long studentId, Long yearId, Long cvsId) {
        return invoiceRepository.findByStudentIdAndYearIdAndClassroomVersionSectionIdAndInvoicePaymentStatus(
                studentId,
                yearId,
                cvsId,
                InvoicePaymentStatus.UNPAID
        );
    }

    // Already Paid Fee & Months
    public Map<String, Object> getAlreadyPaidData(Long studentId, Long yearId, Long cvsId) {
        List<Invoice> invoices = invoiceRepository.findByStudentIdAndYearIdAndClassroomVersionSectionId(
                studentId,
                yearId,
                cvsId
        );

        Set<FeeType> paidFeeTypes = new HashSet<>();
        Set<Month> paidMonths = new HashSet<>();

        for (Invoice inv : invoices) {
            if (inv.getInvoicePaymentStatus() == InvoicePaymentStatus.PAID) {
                for (InvoiceItem item : inv.getInvoiceItems()) {
                    if (item.getFeeType() == FeeType.TUITION_FEE) {
                        paidMonths.add(item.getMonth());
                    } else {
                        paidFeeTypes.add(item.getFeeType());
                    }
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("paidFeeTypes", paidFeeTypes);
        map.put("paidMonths", paidMonths);
        return map;
    }

    public void invoiceDelete(Long id){
        invoiceRepository.deleteById(id);
    }

    public List<Invoice> invoiceList(String invoiceNo) {
        if (invoiceNo != null && !invoiceNo.isEmpty()) {
            return invoiceRepository.findByInvoiceNoContainingIgnoreCase(invoiceNo);
        }
        return invoiceRepository.findAll(Sort.by(Sort.Direction.DESC, "invoiceDate"));
    }
}