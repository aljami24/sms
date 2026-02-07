package com.smha.sms.systemConfiguration.fee.model.entity;

import com.smha.sms.academic.model.entity.ClassroomVersionSection;
import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.entity.PaymentType;
import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class Fees extends BaseEntity {

    private Double feesAmount;
    @ManyToOne
    @JoinColumn(name = "cvs_id")
    private ClassroomVersionSection cvs;
    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    @Override
    public String toString() {
        return "Fees{" +
                "feesAmount=" + feesAmount +
                ", cvsId=" + cvs +
                ", paymentType=" + paymentType +
                ", yearId=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fees fees = (Fees) o;
        return Objects.equals(feesAmount, fees.feesAmount) && Objects.equals(cvs, fees.cvs) && Objects.equals(paymentType, fees.paymentType) && Objects.equals(year, fees.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feesAmount, cvs, paymentType, year);
    }
}
