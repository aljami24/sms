package com.smha.sms.academic.model.entity;

import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name; // A, B
}
