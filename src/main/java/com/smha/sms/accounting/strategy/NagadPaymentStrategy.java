package com.smha.sms.accounting.strategy;

import com.smha.sms.accounting.model.dto.PaymentRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.Payment;
import com.smha.sms.accounting.model.enums.PaymentMethod;

public class NagadPaymentStrategy implements PaymentStrategy {

    @Override
    public Payment pay(Invoice invoice, PaymentRequest paymentRequest) {

        return null;
    }

    @Override
    public String getType() {
        return PaymentMethod.NAGAD.name();
    }
}
