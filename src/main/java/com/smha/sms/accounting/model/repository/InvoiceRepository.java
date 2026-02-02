package com.smha.sms.accounting.model.repository;

import com.smha.sms.accounting.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}