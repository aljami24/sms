package com.smha.sms.accounting.strategy;

import com.smha.sms.accounting.model.dto.PaymentRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.Payment;
import com.smha.sms.accounting.model.enums.PaymentMethod;
import com.smha.sms.common.util.Helper;

import java.time.LocalDateTime;

public class CashPaymentStrategy implements PaymentStrategy{

    @Override
    public Payment pay(Invoice invoice, PaymentRequest paymentRequest) {

        return Payment.builder()
                .invoice(invoice)
                .paymentMethod(PaymentMethod.CASH)
                .amount(paymentRequest.getAmount())
                .transactionNo(Helper.generateTransactionNumber())
                .transactionDate(LocalDateTime.now())
                .build();

    }

    @Override
    public String getType() {
        return PaymentMethod.CASH.name();
    }
}
