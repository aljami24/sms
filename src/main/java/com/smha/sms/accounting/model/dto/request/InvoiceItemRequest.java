package com.smha.sms.accounting.model.dto.request;

import com.smha.sms.accounting.model.enums.FeeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemRequest {

    private boolean disabled;

    private FeeType feeType;

    private BigDecimal perFeeAmount;

    private boolean selected;

    private List<Month> months;

    private String remarks;
}