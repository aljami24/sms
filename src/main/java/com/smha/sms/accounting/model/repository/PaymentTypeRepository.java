package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}