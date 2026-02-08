package com.smha.sms.systemConfiguration.fee.model.mapper;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.repository.ClassroomVersionSectionRepository;
import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.accounting.model.repository.PaymentTypeRepository;
import com.smha.sms.systemConfiguration.fee.model.dto.request.FeeRequestDto;
import com.smha.sms.systemConfiguration.fee.model.dto.response.FeeResponseDto;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeeMapper {

    private final ClassroomVersionSectionRepository classroomVersionSectionRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final YearRepository yearRepository;

    public FeeResponseDto mapToFeeResponse(Fee fee) {
        FeeResponseDto dto = new FeeResponseDto();
        dto.setId(fee.getId());
        dto.setFeesAmount(fee.getFeesAmount());
        dto.setPaymentTypeId(fee.getPaymentTypeId());
        dto.setYearId(fee.getYearId());
        dto.setCvs(fee.getCvs());

        if (fee.getCvs() != null) {
            if (fee.getCvs().getClassRoom() != null) {
                dto.setClassRoomId(fee.getCvs().getClassRoom().getId());
                dto.setClassRoomName(fee.getCvs().getClassRoom().getName());
            }
            if (fee.getCvs().getVersion() != null) {
                dto.setVersionId(fee.getCvs().getVersion().getId());
                dto.setVersionName(fee.getCvs().getVersion().getName());
            }
            if (fee.getCvs().getSection() != null) {
                dto.setSectionId(fee.getCvs().getSection().getId());
                dto.setSectionName(fee.getCvs().getSection().getName());
            }
        }

        if (fee.getYearId() != null) {
            dto.setYearName(fee.getYearId().getName());
        }
        return dto;
    }

    public FeeRequestDto mapToFeeRequestDto(Fee fee) {
        FeeRequestDto dto = new FeeRequestDto();
        dto.setFeesAmount(fee.getFeesAmount());
        dto.setPaymentTypeId(fee.getPaymentTypeId() != null ? fee.getPaymentTypeId().getId() : null);
        dto.setYearId(fee.getYearId() != null ? fee.getYearId().getId() : null);

        if (fee.getCvs() != null) {
            dto.setClassRoomId(fee.getCvs().getClassRoom() != null ? fee.getCvs().getClassRoom().getId() : null);
            dto.setVersionId(fee.getCvs().getVersion() != null ? fee.getCvs().getVersion().getId() : null);
            dto.setSectionId(fee.getCvs().getSection() != null ? fee.getCvs().getSection().getId() : null);
        }

        return dto;
    }

    public void mapDtoToEntity(Fee fee, FeeRequestDto dto) {
        fee.setFeesAmount(dto.getFeesAmount());

        if (dto.getYearId() != null) {
            yearRepository.findById(dto.getYearId()).ifPresent(fee::setYearId);
        }

        if (dto.getPaymentTypeId() != null) {
            paymentTypeRepository.findById(dto.getPaymentTypeId()).ifPresent(fee::setPaymentTypeId);
        }

        if (dto.getClassRoomId() != null && dto.getVersionId() != null) {
            Optional<ClassroomVersionSection> cvsOptional = classroomVersionSectionRepository
                    .findByClassRoomIdAndVersionIdAndSectionId(
                            dto.getClassRoomId(),
                            dto.getVersionId(),
                            dto.getSectionId()
                    );
            cvsOptional.ifPresent(fee::setCvs);
        }
    }
}
