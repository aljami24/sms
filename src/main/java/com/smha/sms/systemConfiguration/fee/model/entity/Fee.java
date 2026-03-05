package com.smha.sms.systemConfiguration.fee.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.entity.PaymentType;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "fees")
public class Fee extends BaseEntity {

    private Double feesAmount;
    @ManyToOne
    @JoinColumn(name = "cvs_id")
    private ClassroomVersionSection classroomVersionSection;

    @Enumerated(EnumType.STRING)
    private FeeType feeType;

    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year yearId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fee fee = (Fee) o;
        return Objects.equals(feesAmount, fee.feesAmount) && Objects.equals(classroomVersionSection, fee.classroomVersionSection) && feeType == fee.feeType && Objects.equals(yearId, fee.yearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feesAmount, classroomVersionSection, feeType, yearId);
    }
}
