package com.smha.sms.systemConfiguration.payScale.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayScaleRequestDto {

    private String name;
    private Double payScaleAmount;
    private Long yearId;
}
