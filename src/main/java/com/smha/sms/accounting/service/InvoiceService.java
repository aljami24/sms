package com.smha.sms.accounting.service;

import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.InvoiceItem;
import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.accounting.model.repository.InvoiceRepository;
import com.smha.sms.common.util.Helper;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.repository.StudentRepository;
import com.smha.sms.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final StudentService studentService;
    private final StudentFeeService studentFeeService;
    private final YearRepository yearRepository;


    public Invoice gennerateInvoice(Long studentId, Long yearId, List<InvoiceItem> invoiceItems) {

        Student student = studentService.getStudent(studentId);
        Year year = yearRepository.findById(yearId).orElseThrow();


        Invoice invoice = new Invoice();

        invoice.setStudent(student);
        invoice.setInvoiceNo(Helper.generateInvoiceNumber());
        invoice.setInvoiceDate(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<InvoiceItem> invoiceItemList = new ArrayList<>();

        for (InvoiceItem item : invoiceItems) {
            item.setInvoice(invoice);
            totalAmount = totalAmount.add(item.getAmount());
            invoiceItemList.add(item);

        }

        invoice.setAmount(totalAmount);
        invoice.setDueAmount(totalAmount);
        invoice.setInvoiceItems(invoiceItemList);
        invoice.setStatus(PaymentStatus.DUE);
        invoice.setYear(year);

        return invoiceRepository.save(invoice);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoice(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }
}
