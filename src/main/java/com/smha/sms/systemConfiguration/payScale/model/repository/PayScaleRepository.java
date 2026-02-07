package com.smha.sms.systemConfiguration.payScale.model.repository;

import com.smha.sms.systemConfiguration.payScale.model.entity.PayScale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayScaleRepository extends JpaRepository<PayScale, Long> {
}
