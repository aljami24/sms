package com.smatik.sms.common.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
public class PoliceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    @JsonIgnore
    private District district;

    private Boolean active = true;
}

