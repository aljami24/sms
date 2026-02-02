package com.smha.sms.user.controller;

import com.smha.sms.user.model.entity.Role;
import com.smha.sms.user.model.repository.PermissionRepository;
import com.smha.sms.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ROLE')")
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final PermissionRepository permissionRepository;

    @GetMapping("/list")
    public String roleList(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "configuration/roleList";
    }


    @GetMapping("/form")
    public String roleForm(@RequestParam(required = false) Long id, Model model) {
        Role role = (id != null) ? roleService.getRoleById(id) : new Role();
        model.addAttribute("role", role);
        model.addAttribute("permissions", permissionRepository.findAll());
        return "configuration/roleForm";
    }

    @PostMapping("/save")
    public String saveRole(@ModelAttribute Role role,
                           @RequestParam(value = "permissionIds", required = false) List<Long> permissionIds) {
        roleService.saveOrUpdate(role, permissionIds);
        return "redirect:/role/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return "redirect:/role/list";
    }

}