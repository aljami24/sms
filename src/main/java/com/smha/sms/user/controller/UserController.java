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

import java.util.List;
@Controller
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    /* ================= FORM ================= */

    @GetMapping("/form")
    public String createForm(Model model) {
        model.addAttribute("userDto", new UserAccessDto());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("permissions", permissionRepository.findAll());
        return "configuration/userForm";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();

        UserAccessDto dto = new UserAccessDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoleIds(user.getRoles().stream().map(Role::getId).toList());
        dto.setPermissionIds(user.getPermissions().stream().map(Permission::getId).toList());

        // Thymeleaf এ roles names pathano
        List<String> roleNames = user.getRoles().stream().map(Role::getName).toList();
        model.addAttribute("roleNames", roleNames);

        model.addAttribute("userDto", dto);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("permissions", permissionRepository.findAll());

        return "configuration/userForm";
    }

    /* ================= ACTION ================= */

    @PostMapping("/save")
    public String create(@ModelAttribute("userDto") UserAccessDto dto) {
        userService.create(dto);
        return "redirect:/user/list";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("userDto") UserAccessDto dto) {
        userService.update(dto);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<User> user = userService.userList();
        model.addAttribute("users",user );
        return "configuration/userList";
    }

    @PostMapping("/delete/{id}")
    public String deleteId(@PathVariable Long id){
         userService.delete(id);
         return "redirect:/user/list";
    }
}
