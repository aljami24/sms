package com.smatik.sms.common.address.dto;

import com.smatik.sms.common.address.entity.District;
import com.smatik.sms.common.address.entity.Division;
import com.smatik.sms.common.address.entity.PoliceStation;
import com.smatik.sms.common.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

/**
 * AddressRequestDto
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Getter
@Setter
public class AddressResponseDto {
    private String village;
    private AddressType addressType;
    private Division division;
    private District district;
    private PoliceStation policeStation;
}
