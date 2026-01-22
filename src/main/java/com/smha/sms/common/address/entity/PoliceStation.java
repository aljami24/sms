package com.smha.sms.common.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * PoliceStation Entity
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Entity
@Getter
@Setter
@Table(name = "police_stations")
public class PoliceStation extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nameBn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    @JsonIgnore
    private District district;

    private Boolean active = true;


    @Override
    public String toString() {
        return "PoliceStation{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", nameBn" + nameBn + '\'' +
                ", district=" + district +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PoliceStation that = (PoliceStation) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(name, that.name) && Objects.equals(nameBn, that.nameBn) && Objects.equals(district, that.district) && Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, nameBn, district, active);
    }
}

