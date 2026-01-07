package com.smatik.sms.employee.model.entity;

import com.smatik.sms.common.address.entity.Address;
import com.smatik.sms.common.enums.EmployeeType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;
    private String name;
    private Gender gender;
    private LocalDate dob;
    private LocalDate joiningDate;
    private Double salary;
    private EmployeeType employeeType;
    private IdentityType identityType;
    private String identityNumber;
    private String phoneNumber;
    private String photoDir;
    private String nidDir;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();

    @Override
    public String toString() {
        return "EmployEntity{" +
                "id=" + id +
                ", EmployId=" + employeeId +
                ", Name='" + name + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", joiningDate=" + joiningDate +
                ", salary=" + salary +
                ", employType=" + employeeType +
                ", identityType=" + identityType +
                ", identityNumber=" + identityNumber +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", photoDir='" + photoDir + '\'' +
                ", nidDir='" + nidDir + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(employeeId, employee.employeeId) && Objects.equals(name, employee.name) && gender == employee.gender && Objects.equals(dob, employee.dob) && Objects.equals(joiningDate, employee.joiningDate) && Objects.equals(salary, employee.salary) && employeeType == employee.employeeType && identityType == employee.identityType && Objects.equals(identityNumber, employee.identityNumber) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(photoDir, employee.photoDir) && Objects.equals(nidDir, employee.nidDir) && Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, name, gender, dob, joiningDate, salary, employeeType, identityType, identityNumber, phoneNumber, photoDir, nidDir, address);
    }
}
