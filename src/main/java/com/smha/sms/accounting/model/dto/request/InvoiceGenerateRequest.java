package com.smha.sms.accounting.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceGenerateRequest {
    private Long studentId;

    private Long classroomVersionSectionId;

    private Long yearId;

    private LocalDateTime invoiceDate;

    private List<InvoiceItemRequest> items = new ArrayList<>();

    private BigDecimal totalAmount;
}