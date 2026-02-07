package com.smha.sms.systemConfiguration.fee.model.repository;

import com.smha.sms.systemConfiguration.fee.model.entity.Fees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fees, Long> {
}
