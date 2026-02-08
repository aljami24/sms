package com.smha.sms.systemConfiguration.fee.model.repository;

import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee, Long> {
}
