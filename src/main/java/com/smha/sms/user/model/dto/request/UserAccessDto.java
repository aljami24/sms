package com.smha.sms.user.model.dto.request;


import com.smha.sms.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAccessDto extends BaseEntity {

    private String username;
    private String password;

    private List<Long> roleIds = new ArrayList<>();
    private List<Long> permissionIds = new ArrayList<>();
}