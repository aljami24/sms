package com.smha.sms.accounting.model.entity;

import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRecord extends BaseEntity {

    private LocalDate date;
    private Double amount;

    @Enumerated (EnumType.STRING)
    private Month month;

    @ManyToOne
    @JoinColumn(name ="year_id")
    private Year year;

    @ManyToOne
    @JoinColumn(name ="expense_type_id")
    private ExpenseType expenseType;

    private String description;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Override
    public String toString() {
        return "ExpenseRecord{" +
                "date=" + date +
                ", amount=" + amount +
                ", month=" + month +
                ", year=" + year +
                ", expenseType='" + expenseType + '\'' +
                ", description='" + description + '\'' +
                ", employee=" + employee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseRecord that = (ExpenseRecord) o;
        return Objects.equals(date, that.date) && Objects.equals(amount, that.amount) && month == that.month && Objects.equals(year, that.year) && Objects.equals(expenseType, that.expenseType) && Objects.equals(description, that.description) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount, month, year, expenseType, description, employee);
    }
}
