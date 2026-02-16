package com.smha.sms.accounting.service;

import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoice(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }
}
