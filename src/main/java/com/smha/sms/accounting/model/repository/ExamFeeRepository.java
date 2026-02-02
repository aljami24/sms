package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.ExamFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamFeeRepository extends JpaRepository<ExamFee, Long> {
}