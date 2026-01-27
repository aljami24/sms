package com.smha.sms.user.controller;

import com.smha.sms.user.service.LoggedInUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class LoggedInUserController {

    private final LoggedInUserService loggedInUserService;

    @ModelAttribute
    public void addLoggedInUser(Model model) {
        model.addAttribute("user", loggedInUserService.getCurrentUser());
    }
}