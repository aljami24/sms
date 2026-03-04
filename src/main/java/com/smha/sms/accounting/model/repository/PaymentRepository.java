package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTransactionNoContainingIgnoreCase(String transactionNo);
}