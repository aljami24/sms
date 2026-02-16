package com.smha.sms.accounting.model.entity;


import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Month;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem extends BaseEntity {

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Enumerated (EnumType.STRING)
    private FeeType feeType;

    private BigDecimal amount;

    @Enumerated (EnumType.STRING)
    private Month month;

    private String remarks;
}
