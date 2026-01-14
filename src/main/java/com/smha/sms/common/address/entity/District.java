package com.smha.sms.common.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * District Entity
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Getter
@Setter
@Entity
@Table(name = "districts")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    @JsonIgnore
    private Division division;

    private Boolean active = true;

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", division=" + division +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return Objects.equals(id, district.id) && Objects.equals(name, district.name) && Objects.equals(division, district.division) && Objects.equals(active, district.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, division, active);
    }
}

