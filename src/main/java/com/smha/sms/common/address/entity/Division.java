package com.smha.sms.common.address.entity;

import com.smha.sms.common.entity.BaseEntity;
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
public class Division extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String nameBn;

    private Boolean active = true;

    @Override
    public String toString() {
        return "Division{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", nameBn" + nameBn + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Division division = (Division) o;
        return Objects.equals(getId(), division.getId()) && Objects.equals(name, division.name) && Objects.equals(nameBn, division.nameBn) && Objects.equals(active, division.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, nameBn, active);
    }
}
