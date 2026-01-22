package com.smha.sms.attendance.model.entity;

import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.common.enums.MonthName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Year yearId;
    @Enumerated(EnumType.STRING)
    private MonthName monthName;
    private int totalDay;
    private int workingDay;
    private int holyDay;

    @Override
    public String toString() {
        return "MonthlyHoliday{" +
                "id=" + id +
                ", yearId=" + yearId +
                ", monthName=" + monthName +
                ", totalDay=" + totalDay +
                ", workingDay=" + workingDay +
                ", holyDay=" + holyDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MonthlyHoliday that = (MonthlyHoliday) o;
        return totalDay == that.totalDay && workingDay == that.workingDay && holyDay == that.holyDay && Objects.equals(id, that.id) && Objects.equals(yearId, that.yearId) && monthName == that.monthName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, yearId, monthName, totalDay, workingDay, holyDay);
    }
}
