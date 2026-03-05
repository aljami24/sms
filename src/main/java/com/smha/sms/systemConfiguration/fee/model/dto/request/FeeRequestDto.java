package com.smha.sms.systemConfiguration.fee.model.dto.request;

import com.smha.sms.accounting.model.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeeRequestDto {

    private Double feesAmount;
    private Long classRoomId;
    private Long versionId;
    private Long sectionId;
    private FeeType feeType;
    private Long yearId;
}
