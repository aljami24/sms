package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
}