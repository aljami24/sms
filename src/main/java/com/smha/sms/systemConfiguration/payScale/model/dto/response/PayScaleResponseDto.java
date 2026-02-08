package com.smha.sms.systemConfiguration.payScale.model.dto.response;

import com.smha.sms.academic.model.entity.Year;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayScaleResponseDto {

    private Long id;
    private String name;
    private Double payScaleAmount;
    private Year year;
    private Long yearId;
    private String yearName;
}
