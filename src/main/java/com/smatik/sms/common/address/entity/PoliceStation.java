package com.smatik.sms.common.address.entity;

import jakarta.persistence.*;

/**
 * PoliceStation Entity
 *
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Entity
@Table(name = "police_stations")
public class PoliceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    private Boolean active = true;
}

