package com.smha.sms.systemConfiguration.payScale.model.mapper;

import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.systemConfiguration.payScale.model.dto.request.PayScaleRequestDto;
import com.smha.sms.systemConfiguration.payScale.model.dto.response.PayScaleResponseDto;
import com.smha.sms.systemConfiguration.payScale.model.entity.PayScale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayScaleMapper {

    private final YearRepository yearRepository;

    public PayScaleResponseDto mapToPayScaleResponse(PayScale payScale) {
        PayScaleResponseDto dto = new PayScaleResponseDto();
        dto.setId(payScale.getId());
        dto.setName(payScale.getName());
        dto.setPayScaleAmount(payScale.getPayScaleAmount());
        dto.setYear(payScale.getYear());
        dto.setYearId(payScale.getYear() != null ? payScale.getYear().getId() : null);

        if (payScale.getYear() != null) {
            dto.setYearName(payScale.getYear().getName());
        }

        return dto;
    }

    public PayScaleRequestDto mapToPayScaleRequestDto(PayScale payScale) {
        PayScaleRequestDto dto = new PayScaleRequestDto();
        dto.setName(payScale.getName());
        dto.setPayScaleAmount(payScale.getPayScaleAmount());
        dto.setYearId(payScale.getYear() != null ? payScale.getYear().getId() : null);
        return dto;
    }

    public void mapDtoToEntity(PayScale payScale, PayScaleRequestDto dto) {
        payScale.setName(dto.getName());
        payScale.setPayScaleAmount(dto.getPayScaleAmount());

        if (dto.getYearId() != null) {
            yearRepository.findById(dto.getYearId()).ifPresent(payScale::setYear);
        }
    }
}
