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
        dto.setFeeType(fee.getFeeType());
        dto.setYearId(fee.getYearId());
        dto.setClassroomVersionSection(fee.getClassroomVersionSection());

        if (fee.getClassroomVersionSection() != null) {
            if (fee.getClassroomVersionSection().getClassRoom() != null) {
                dto.setClassRoomId(fee.getClassroomVersionSection().getClassRoom().getId());
                dto.setClassRoomName(fee.getClassroomVersionSection().getClassRoom().getName());
            }
            if (fee.getClassroomVersionSection().getVersion() != null) {
                dto.setVersionId(fee.getClassroomVersionSection().getVersion().getId());
                dto.setVersionName(fee.getClassroomVersionSection().getVersion().getName());
            }
            if (fee.getClassroomVersionSection().getSection() != null) {
                dto.setSectionId(fee.getClassroomVersionSection().getSection().getId());
                dto.setSectionName(fee.getClassroomVersionSection().getSection().getName());
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
        dto.setFeeType(fee.getFeeType());
        dto.setYearId(fee.getYearId() != null ? fee.getYearId().getId() : null);

        if (fee.getClassroomVersionSection() != null) {
            dto.setClassRoomId(fee.getClassroomVersionSection().getClassRoom() != null ? fee.getClassroomVersionSection().getClassRoom().getId() : null);
            dto.setVersionId(fee.getClassroomVersionSection().getVersion() != null ? fee.getClassroomVersionSection().getVersion().getId() : null);
            dto.setSectionId(fee.getClassroomVersionSection().getSection() != null ? fee.getClassroomVersionSection().getSection().getId() : null);
        }

        return dto;
    }

    public void mapDtoToEntity(Fee fee, FeeRequestDto dto) {
        fee.setFeesAmount(dto.getFeesAmount());

        fee.setFeeType(dto.getFeeType());

        if (dto.getYearId() != null) {
            yearRepository.findById(dto.getYearId()).ifPresent(fee::setYearId);
        }

        if (dto.getClassRoomId() != null && dto.getVersionId() != null) {
            Optional<ClassroomVersionSection> cvsOptional = classroomVersionSectionRepository
                    .findByClassRoomIdAndVersionIdAndSectionId(
                            dto.getClassRoomId(),
                            dto.getVersionId(),
                            dto.getSectionId()
                    );
            cvsOptional.ifPresent(fee::setClassroomVersionSection);
        }
    }
}
