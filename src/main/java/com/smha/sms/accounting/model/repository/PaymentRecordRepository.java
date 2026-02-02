package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
}