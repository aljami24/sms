package com.smha.sms.user.service;


import com.smha.sms.user.model.entity.Permission;
import com.smha.sms.user.model.repository.PermissionRepository;
import com.smha.sms.user.model.repository.RoleRepository;
import com.smha.sms.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    //  list
    public List<Permission> getAll() {
        return permissionRepository.findAll();
    }

    //  save
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }

    //  delete
    public void delete(Long permissionId) {

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        // 🔹 Role থেকে remove
        roleRepository.findAll().forEach(role -> {
            role.getPermissions().remove(permission);
        });

        // 🔹 User থেকে remove
        userRepository.findAll().forEach(user -> {
            user.getPermissions().remove(permission);
        });

        // 🔹 এখন safe delete
        permissionRepository.delete(permission);
    }

    // (optional) edit support
    public Permission getById(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }
}
