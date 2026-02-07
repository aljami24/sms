package com.smha.sms.employee.model.entity;

import com.smha.sms.common.address.entity.Address;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.common.enums.AddressType;
import com.smha.sms.common.enums.EmployeeType;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import com.smha.sms.employee.model.enums.EmployeeStatus;
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

public class Employee extends BaseEntity {

    private Long employeeId;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dob;

    private LocalDate joiningDate;
    private Double salary;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    private String identityNumber;
    private String phoneNumber;
    private String photoDir;
    private String nidDir;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private Boolean active = true;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();

    public String getGenderValue() {
        return gender.name();
    }

    public String getPermanentAddress() {
        if (address == null) return " ";
        return address.stream()
                .filter(address -> address.getAddressType() == AddressType.PERMANENT_ADDRESS)
                .map(address -> address.getVillage() + "," + address.getPoliceStation().getName() + "," + address.getDistrict().getName())
                .findFirst().orElse(" ");
    }

    public String getPresentAddress() {
        if (address == null) return "";
        return address.stream()
                .filter(a -> a.getAddressType() == AddressType.PRESENT_ADDRESS)
                .map(a -> a.getVillage() + "," + a.getPoliceStation().getName() + "," + a.getDistrict().getName())
                .findFirst().orElse(" ");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + getId() +
                ", employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", joiningDate=" + joiningDate +
                ", salary=" + salary +
                ", employeeType=" + employeeType +
                ", identityType=" + identityType +
                ", identityNumber='" + identityNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", photoDir='" + photoDir + '\'' +
                ", nidDir='" + nidDir + '\'' +
                ", active=" + active +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getId(), employee.getId()) && Objects.equals(employeeId, employee.employeeId) && Objects.equals(name, employee.name) && gender == employee.gender && Objects.equals(dob, employee.dob) && Objects.equals(joiningDate, employee.joiningDate) && Objects.equals(salary, employee.salary) && employeeType == employee.employeeType && identityType == employee.identityType && Objects.equals(identityNumber, employee.identityNumber) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(photoDir, employee.photoDir) && Objects.equals(nidDir, employee.nidDir) && Objects.equals(active, employee.active) && Objects.equals(address, employee.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), employeeId, name, gender, dob, joiningDate, salary, employeeType, identityType, identityNumber, phoneNumber, photoDir, nidDir, active, address);
    }
}
