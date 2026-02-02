package com.smha.sms.user.service;

import com.smha.sms.user.model.entity.Role;
import com.smha.sms.user.model.repository.PermissionRepository;
import com.smha.sms.user.model.repository.RoleRepository;
import com.smha.sms.user.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public void saveOrUpdate(Role role, List<Long> permissionIds) {
        Role roleToSave;

        if (role.getId() != null) {
            roleToSave = roleRepository.findById(role.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + role.getId()));

            roleToSave.setName(role.getName());
        } else {
            roleToSave = role;
        }

        if (permissionIds != null) {
            roleToSave.setPermissions(new HashSet<>(permissionRepository.findAllById(permissionIds)));
        } else {
            if(roleToSave.getPermissions() != null) {
                roleToSave.getPermissions().clear();
            }
        }

        roleRepository.save(roleToSave);
    }

    public void delete(Long roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        userRepository.findAll().forEach(user -> {
            user.getRoles().remove(role);
        });

        roleRepository.delete(role);
    }
}