package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
}