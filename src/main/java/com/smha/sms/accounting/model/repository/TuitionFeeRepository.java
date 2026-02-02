package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.TuitionFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuitionFeeRepository extends JpaRepository<TuitionFee, Long> {
}