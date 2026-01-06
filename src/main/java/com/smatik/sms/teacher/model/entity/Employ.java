package com.smatik.sms.teacher.model.entity;

import com.smatik.sms.common.address.entity.Address;
import com.smatik.sms.common.enums.EmployType;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

public class Employ {

    private Long id;
    private Long EmployId;
    private String Name;
    private Gender gender;
    private LocalDate dob;
    private LocalDate joiningDate;
    private Double salary;
    private EmployType employType;
    private IdentityType identityType;
    private Long identityNumber;
    private String phoneNumber;
    private String photo;
    private String photoDir;
    private String nid;
    private String nidDir;

    @OneToMany(mappedBy = "employ", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address = new ArrayList<>();

    @Override
    public String toString() {
        return "EmployEntity{" +
                "id=" + id +
                ", EmployId=" + EmployId +
                ", Name='" + Name + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", joiningDate=" + joiningDate +
                ", salary=" + salary +
                ", employType=" + employType +
                ", identityType=" + identityType +
                ", identityNumber=" + identityNumber +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                ", photoDir='" + photoDir + '\'' +
                ", nid='" + nid + '\'' +
                ", nidDir='" + nidDir + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employ employ = (Employ) o;
        return Objects.equals(id, employ.id) && Objects.equals(EmployId, employ.EmployId) && Objects.equals(Name, employ.Name) && gender == employ.gender && Objects.equals(dob, employ.dob) && Objects.equals(joiningDate, employ.joiningDate) && Objects.equals(salary, employ.salary) && employType == employ.employType && identityType == employ.identityType && Objects.equals(identityNumber, employ.identityNumber) && Objects.equals(phoneNumber, employ.phoneNumber) && Objects.equals(photo, employ.photo) && Objects.equals(photoDir, employ.photoDir) && Objects.equals(nid, employ.nid) && Objects.equals(nidDir, employ.nidDir) && Objects.equals(address, employ.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, EmployId, Name, gender, dob, joiningDate, salary, employType, identityType, identityNumber, phoneNumber, photo, photoDir, nid, nidDir, address);
    }
}
