package com.smha.sms.accounting.model.dto.response;

import com.smha.sms.accounting.model.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemResponse {

    private FeeType feeType;

    private BigDecimal amount;

    private Month month;

    private String remarks;
}