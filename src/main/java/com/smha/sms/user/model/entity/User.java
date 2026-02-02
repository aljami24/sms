package com.smha.sms.user.model.entity;

import com.smha.sms.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{

    private String username;
    private String password;

    // Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // Direct user permissions
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    // Combined permissions from roles + direct user permissions
    public Set<String> getAllPermissionCodes() {
        Set<String> allPermissions = new HashSet<>();

//        // Role permissions
//        roles.forEach(role ->
//                role.getPermissions()
//                        .forEach(p -> allPermissions.add(p.getCode()))
//        );

        // Direct user permissions
        permissions.forEach(p -> allPermissions.add(p.getCode()));

        return allPermissions;
    }

    public Set<String> getUserRoles() {
        Set<String> roleNames = new HashSet<>();
        roles.forEach(r -> roleNames.add(r.getName()));
        return roleNames;
    }
}
