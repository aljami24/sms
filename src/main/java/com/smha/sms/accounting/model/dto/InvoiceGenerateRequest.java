package com.smha.sms.accounting.model.dto;

import com.smha.sms.accounting.model.enums.FeeType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceGenerateRequest {

    @NotNull
    private Long studentId;

}
