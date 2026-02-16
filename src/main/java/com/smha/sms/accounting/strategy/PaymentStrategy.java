package com.smha.sms.accounting.strategy;

import com.smha.sms.accounting.model.dto.PaymentRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public interface PaymentStrategy {
    Payment pay(Invoice invoice, PaymentRequest paymentRequest);
    String getType();
}
