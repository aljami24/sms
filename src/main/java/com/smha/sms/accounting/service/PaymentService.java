package com.smha.sms.accounting.service;


import com.smha.sms.accounting.factory.PaymentProcessorFactory;
import com.smha.sms.accounting.model.dto.PaymentRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.Payment;
import com.smha.sms.accounting.model.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentProcessorFactory paymentProcessorFactory;
    private final InvoiceService invoiceService;
    private final PaymentRepository paymentRepository;

    public Payment collect(PaymentRequest paymentRequest, Long invoiceId) {
        Invoice invoice = invoiceService.getInvoice(invoiceId).orElse(null);
        Payment payment = paymentProcessorFactory.getPaymentStrategy(paymentRequest.getPaymentMethod()).pay(invoice, paymentRequest);
        paymentRepository.save(payment);
        return payment;
    }

}
