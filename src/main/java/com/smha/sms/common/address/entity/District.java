package com.smha.sms.common.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smha.sms.common.entity.BaseEntity;
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
public class District extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nameBn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    @JsonIgnore
    private Division division;

    private Boolean active = true;

    @Override
    public String toString() {
        return "District{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", nameBn" + nameBn + '\'' +
                ", division=" + division +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return Objects.equals(getId(), district.getId()) && Objects.equals(name, district.name) && Objects.equals(nameBn, district.nameBn) && Objects.equals(division, district.division) && Objects.equals(active, district.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, nameBn, division, active);
    }
}

