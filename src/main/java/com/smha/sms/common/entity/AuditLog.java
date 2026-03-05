package com.smha.sms.common.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuditLog<T> extends BaseEntity {

    private String username;
    private String actionType;
    private String actionData;
    private String remark;

    @Override
    public String toString() {
        return "AuditLog{" +
                "username='" + username + '\'' +
                ", actionType='" + actionType + '\'' +
                ", actionData='" + actionData + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
