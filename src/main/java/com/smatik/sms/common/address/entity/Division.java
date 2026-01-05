package com.smatik.sms.common.address.entity;

import jakarta.persistence.*;

/**
 * Division Entity
 *
 * Author: jami
 * Created On: 2026-01-05
 * Module:
 */

@Entity
@Table(name = "divisions")
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private Boolean active = true;
}
