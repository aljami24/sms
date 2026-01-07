package com.smatik.sms.common.address.entity;

import com.smatik.sms.common.enums.AddressType;
import com.smatik.sms.student.model.entity.Student;
import com.smatik.sms.employee.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Address Entity
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String village;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = true)
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = true)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "police_station_id", nullable = true)
    private PoliceStation policeStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employ_id")
    private Employee employ;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", village='" + village + '\'' +
                ", addressType=" + addressType +
                ", division=" + division +
                ", district=" + district +
                ", policeStation=" + policeStation +
                ", student=" + student +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(village, address.village) && addressType == address.addressType && Objects.equals(division, address.division) && Objects.equals(district, address.district) && Objects.equals(policeStation, address.policeStation) && Objects.equals(student, address.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, village, addressType, division, district, policeStation, student);
    }
}

