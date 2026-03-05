package com.smha.sms.systemConfiguration.fee.model.dto.response;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.entity.PaymentType;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.entity.StudentAcademicRecord;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeeResponseDto {

    private Long id;
    private Double feesAmount;
    private ClassroomVersionSection classroomVersionSection;
    private FeeType feeType;
    private Long classRoomId;
    private Long versionId;
    private Long sectionId;
    private Year yearId;

    private String classRoomName;
    private String versionName;
    private String sectionName;
    private String yearName;
}
