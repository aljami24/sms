package com.smha.sms.systemConfiguration.fee.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.entity.PaymentType;
import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private ClassroomVersionSection cvs;
    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentTypeId;
    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year yearId;

    @Override
    public String toString() {
        return "Fees{" +
                "feesAmount=" + feesAmount +
                ", cvsId=" + cvs +
                ", paymentType=" + paymentTypeId +
                ", yearId=" + yearId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fee fees = (Fee) o;
        return Objects.equals(feesAmount, fees.feesAmount) && Objects.equals(cvs, fees.cvs) && Objects.equals(paymentTypeId, fees.paymentTypeId) && Objects.equals(yearId, fees.yearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feesAmount, cvs, paymentTypeId, yearId);
    }
}
