package com.smha.sms.accounting.factory;

import com.smha.sms.accounting.model.enums.PaymentMethod;
import com.smha.sms.accounting.strategy.PaymentStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PaymentProcessorFactory {

    private final Map<PaymentMethod, PaymentStrategy> strategyMap;

    public PaymentProcessorFactory(List<PaymentStrategy> strategies) {
        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        s -> PaymentMethod.valueOf(s.getType()),
                        s -> s
                ));
        System.out.println("Loaded strategies: " + strategies.size());
    }

    public PaymentStrategy getPaymentStrategy(PaymentMethod method) {
        PaymentStrategy strategy = strategyMap.get(method);


        if (strategy == null) {
            throw new RuntimeException("No payment strategy found for: " + method);
        }

        return strategy;
    }

//    private Map<String, PaymentStrategy> paymentStrategyMap;
//
//
//    public PaymentProcessorFactory(List<PaymentStrategy> paymentStrategyList) {
//        this.paymentStrategyMap = paymentStrategyList.stream()
//                .collect(Collectors.toMap(PaymentStrategy::getType, p -> p));
//    }
//
//    public PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
//        return paymentStrategyMap.get(paymentMethod.toString());
//    }


}