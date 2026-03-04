package com.smha.sms.accounting.service;


import com.smha.sms.accounting.factory.PaymentProcessorFactory;
import com.smha.sms.accounting.model.dto.request.PaymentRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.Payment;
import com.smha.sms.accounting.model.enums.InvoicePaymentStatus;
import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.accounting.model.repository.InvoiceRepository;
import com.smha.sms.accounting.model.repository.PaymentRepository;
import com.smha.sms.common.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentProcessorFactory paymentProcessorFactory;
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    public Payment collects(PaymentRequest request) {

        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.getInvoicePaymentStatus() == InvoicePaymentStatus.PAID) {
            throw new RuntimeException("Invoice already paid.");
        }

        if (request.getAmount().compareTo(invoice.getDueAmount()) > 0) {
            throw new RuntimeException("Amount exceeds due.");
        }

        // Strategy call (gateway logic only)
        Payment payment = paymentProcessorFactory
                .getPaymentStrategy(request.getPaymentMethod())
                .pay(invoice, request);

        payment.setInvoice(invoice);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionNo(Helper.generateTransactionNumber());
        payment.setTransactionDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.SUCCESS);

        payment.setPreviousDueAmount(invoice.getDueAmount());

        // 🔥 Update invoice
        BigDecimal newPaidAmount = invoice.getPaidAmount().add(request.getAmount());
        BigDecimal newDueAmount = invoice.getTotalAmount().subtract(newPaidAmount);

        invoice.setPaidAmount(newPaidAmount);
        invoice.setDueAmount(newDueAmount);

        payment.setRemainingDueAmount(newDueAmount);

        if (newDueAmount.compareTo(BigDecimal.ZERO) == 0) {
            invoice.setInvoicePaymentStatus(InvoicePaymentStatus.PAID);
        } else {
            invoice.setInvoicePaymentStatus(InvoicePaymentStatus.PARTIAL);
        }

        paymentRepository.save(payment);
        invoiceRepository.save(invoice);

        return payment;
    }

    public List<Payment> paymentList(String transactionNo){

        if (transactionNo != null && !transactionNo.trim().isEmpty()) {
            return paymentRepository
                    .findByTransactionNoContainingIgnoreCase(transactionNo);
        }

        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }
}