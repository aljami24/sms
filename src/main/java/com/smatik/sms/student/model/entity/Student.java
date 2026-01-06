package com.smatik.sms.student.model.entity;

import com.smatik.sms.academic.model.entity.ClassroomVersionSection;
import com.smatik.sms.common.address.entity.Address;
import com.smatik.sms.common.enums.Gender;
import com.smatik.sms.common.enums.IdentityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fatherName;
    private String motherName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;
    private Long identityNumber;

    @ManyToOne
    private ClassroomVersionSection classroomVersionSectionsId;
    private String photoDir;
    private String nidDir;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;


}
