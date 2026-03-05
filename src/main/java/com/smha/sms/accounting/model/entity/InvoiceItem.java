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
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem extends BaseEntity {

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    private String remarks;

    @Column(name = "amount", nullable = false)
    private BigDecimal itemTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeeType feeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Month month;   // ✅ single month only

    @Column(nullable = false)
    private BigDecimal perFeeAmount;

}