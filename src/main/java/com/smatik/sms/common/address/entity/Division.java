package com.smatik.sms.common.address.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Division Entity
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Entity
@Getter
@Setter
@Table(name = "divisions")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Boolean active = true;

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(id, division.id) && Objects.equals(name, division.name) && Objects.equals(active, division.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active);
    }
}
