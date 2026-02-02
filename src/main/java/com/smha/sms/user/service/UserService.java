package com.smha.sms.user.service;

import com.smha.sms.user.model.dto.request.UserAccessDto;
import com.smha.sms.user.model.entity.User;
import com.smha.sms.user.model.repository.PermissionRepository;
import com.smha.sms.user.model.repository.RoleRepository;
import com.smha.sms.user.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(UserAccessDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Assign selected roles
        user.setRoles(new HashSet<>(roleRepository.findAllById(dto.getRoleIds())));

        // Assign selected permissions (overrides)
        user.setPermissions(new HashSet<>(permissionRepository.findAllById(dto.getPermissionIds())));

        userRepository.save(user); // user_permissions table auto update
    }

    public void update(UserAccessDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        user.setRoles(new HashSet<>(roleRepository.findAllById(dto.getRoleIds())));
        user.setPermissions(new HashSet<>(permissionRepository.findAllById(dto.getPermissionIds())));

        userRepository.save(user);
    }

    public List<User> userList() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}