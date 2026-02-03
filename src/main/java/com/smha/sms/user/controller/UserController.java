package com.smha.sms.user.controller;

import com.smha.sms.user.model.dto.request.UserAccessDto;
import com.smha.sms.user.model.entity.Permission;
import com.smha.sms.user.model.entity.Role;
import com.smha.sms.user.model.entity.User;
import com.smha.sms.user.model.repository.PermissionRepository;
import com.smha.sms.user.model.repository.RoleRepository;
import com.smha.sms.user.model.repository.UserRepository;
import com.smha.sms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    // CREATE FORM
    @GetMapping("/form")
    public String createForm(Model model) {
        UserAccessDto dto = new UserAccessDto(); // new user, no permission
        model.addAttribute("userDto", dto);

        List<Role> roles = roleRepository.findAll();

        // RoleId -> PermissionId List mapping
        Map<Long, List<Long>> rolePermMap = new HashMap<>();
        for (Role r : roles) {
            rolePermMap.put(r.getId(), r.getPermissions().stream().map(Permission::getId).toList());
        }

        model.addAttribute("roles", roles);
        model.addAttribute("permissions", permissionRepository.findAll()); // all permissions for checkbox div
        model.addAttribute("rolePermMap", rolePermMap);

        return "configuration/userForm";
    }


    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();

        UserAccessDto dto = new UserAccessDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(""); // blank password
        dto.setRoleIds(user.getRoles().stream().map(Role::getId).toList());
        dto.setPermissionIds(user.getPermissions().stream().map(Permission::getId).toList());

        List<Role> roles = roleRepository.findAll();
        List<Permission> allPermissions = permissionRepository.findAll();

        Map<Long, List<Long>> rolePermMap = new HashMap<>();
        for (Role r : roles) {
            rolePermMap.put(r.getId(), r.getPermissions().stream().map(Permission::getId).toList());
        }

        model.addAttribute("userDto", dto);
        model.addAttribute("roles", roles);
        model.addAttribute("permissions", allPermissions);
        model.addAttribute("rolePermMap", rolePermMap);

        return "configuration/userForm";
    }

    // SAVE
    @PostMapping("/save")
    public String create(@ModelAttribute("userDto") UserAccessDto dto) {
        userService.create(dto);
        return "redirect:/user/list";
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("userDto") UserAccessDto dto) {
        userService.update(dto);
        return "redirect:/user/list";
    }

    // LIST
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userService.userList());
        return "configuration/userList";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/user/list";
    }
}