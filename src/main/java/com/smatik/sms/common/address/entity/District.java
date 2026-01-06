package com.smatik.sms.common.address.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
}

