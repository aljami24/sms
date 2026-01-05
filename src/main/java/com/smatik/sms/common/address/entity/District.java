package com.smatik.sms.common.address.entity;

import jakarta.persistence.*;

/**
 * District Entity
 *
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

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
    private Division division;

    private Boolean active = true;
}

