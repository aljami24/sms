package com.smha.sms.systemConfiguration.payScale.model.entity;

import com.smha.sms.academic.model.entity.Year;
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
public class PayScale extends BaseEntity {

    private String name;
    private Double payScaleAmount;
    @ManyToOne
    @JoinColumn(name = "year_id")
    private Year year;

    @Override
    public String toString() {
        return "PayScale{" +
                "name='" + name + '\'' +
                ", payScaleAmount=" + payScaleAmount +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PayScale payScale = (PayScale) o;
        return Objects.equals(name, payScale.name) && Objects.equals(payScaleAmount, payScale.payScaleAmount) && Objects.equals(year, payScale.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, payScaleAmount, year);
    }
}
