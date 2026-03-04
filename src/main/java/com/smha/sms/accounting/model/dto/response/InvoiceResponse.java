package com.smha.sms.accounting.model.dto.response;

import com.smha.sms.accounting.model.enums.PaymentStatus;
import com.smha.sms.systemConfiguration.fee.model.dto.response.FeeResponseDto;
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
public class InvoiceResponse {

    private Long id;

    private String invoiceNo;

    private String studentName;

    private String classroomName;
    private String versionName;
    private String sectionName;
    private String admissionFee;


    private String yearName;

    private LocalDateTime invoiceDate;

    private PaymentStatus status;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private BigDecimal dueAmount;

    private Month month;

    private double totalAmount;

    private List<InvoiceItemResponse> items;

    private List<FeeResponseDto> feeResponseDtoList = new ArrayList<>();
}
