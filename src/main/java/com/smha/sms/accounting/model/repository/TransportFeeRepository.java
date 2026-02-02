package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.TransportFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportFeeRepository extends JpaRepository<TransportFee, Long> {
}