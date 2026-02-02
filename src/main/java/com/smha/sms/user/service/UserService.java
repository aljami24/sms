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

        user.setRoles(new HashSet<>(
                roleRepository.findAllById(dto.getRoleIds())
        ));

        user.setPermissions(new HashSet<>(
                permissionRepository.findAllById(dto.getPermissionIds())
        ));

        userRepository.save(user);
    }

    public void update(UserAccessDto dto) {

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());

        // password only if changed
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        user.setRoles(new HashSet<>(
                roleRepository.findAllById(dto.getRoleIds())
        ));

        user.setPermissions(new HashSet<>(
                permissionRepository.findAllById(dto.getPermissionIds())
        ));

        userRepository.save(user);
    }

    public List<User> userList(){
        List<User> all = userRepository.findAll();
        return all;
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
