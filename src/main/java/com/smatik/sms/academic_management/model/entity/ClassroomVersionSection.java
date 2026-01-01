package com.smatik.sms.academic_management.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomVersionSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassRoom classRoom;

    @ManyToOne
    @JoinColumn(name = "version_id")
    private Version version;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    // স্টুডেন্ট টেবিলের সাথে রিলেশন এখানে হবে (ভবিষ্যতে)
    // @OneToMany(mappedBy = "academicStructure")
    // private List<Student> students;


}
