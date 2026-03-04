package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.PaymentType;
import com.smha.sms.accounting.model.enums.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}