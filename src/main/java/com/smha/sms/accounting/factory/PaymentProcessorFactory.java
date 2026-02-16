package com.smha.sms.accounting.factory;

import com.smha.sms.accounting.model.enums.PaymentMethod;
import com.smha.sms.accounting.strategy.PaymentStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentProcessorFactory {

    private Map<String, PaymentStrategy> paymentStrategyMap;


    public PaymentProcessorFactory(List<PaymentStrategy> paymentStrategyList) {
        this.paymentStrategyMap = paymentStrategyList.stream()
                .collect(Collectors.toMap(PaymentStrategy::getType, p -> p));
    }

    public PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
        return paymentStrategyMap.get(paymentMethod.toString());
    }


}
