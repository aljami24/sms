package com.smha.sms.student.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smha.sms.common.address.entity.Address;
import com.smha.sms.common.entity.BaseEntity;
import com.smha.sms.common.enums.Gender;
import com.smha.sms.common.enums.IdentityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Student Entity
 * Author: jami
 * Created On: 2026-01-04
 * Module: Student Management
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity {

    private Integer registration;
    private String name;
    private String fatherName;
    private String motherName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;
    private String identityNumber;
    private String photoDir;
    private String nidDir;
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;
    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAcademicRecord> studentAcademicRecords;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", registration=" + registration +
                ", name='" + name + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", gender=" + gender +
                ", dob=" + dob +
                ", identityType=" + identityType +
                ", identityNumber='" + identityNumber + '\'' +
                ", photoDir='" + photoDir + '\'' +
                ", nidDir='" + nidDir + '\'' +
                ", addresses=" + addresses +
                ", studentAcademicRecords=" + studentAcademicRecords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(registration, student.registration) && Objects.equals(name, student.name) && Objects.equals(fatherName, student.fatherName) && Objects.equals(motherName, student.motherName) && gender == student.gender && Objects.equals(dob, student.dob) && identityType == student.identityType && Objects.equals(identityNumber, student.identityNumber) && Objects.equals(photoDir, student.photoDir) && Objects.equals(nidDir, student.nidDir) && Objects.equals(addresses, student.addresses) && Objects.equals(studentAcademicRecords, student.studentAcademicRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), registration, name, fatherName, motherName, gender, dob, identityType, identityNumber, photoDir, nidDir, addresses, studentAcademicRecords);
    }
}
