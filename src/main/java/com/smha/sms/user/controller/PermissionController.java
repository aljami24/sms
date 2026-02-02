package com.smha.sms.user.controller;

import com.smha.sms.user.model.entity.Permission;
import com.smha.sms.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('PERMISSION')")
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    //  LIST PAGE
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("permissions", permissionService.getAll());
        return "configuration/permissionList";
    }

    //  FORM PAGE
    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long id, Model model) {

        Permission permission = (id != null)
                ? permissionService.getById(id)
                : new Permission();

        model.addAttribute("permission", permission);
        return "configuration/permissionForm";
    }

    //  SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute Permission permission) {
        permissionService.save(permission);
        return "redirect:/permission/list";
    }

    @PostMapping("/delete/{id}")
    public String deletePermission(@PathVariable Long id) {
        permissionService.delete(id);
        return "redirect:/permission/list";
    }

}
