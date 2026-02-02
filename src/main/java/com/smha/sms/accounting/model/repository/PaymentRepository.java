package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}