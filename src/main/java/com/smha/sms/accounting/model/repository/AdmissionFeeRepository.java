package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.AdmissionFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionFeeRepository extends JpaRepository<AdmissionFee, Long> {
}