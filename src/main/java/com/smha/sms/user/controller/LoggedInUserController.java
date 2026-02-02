package com.smha.sms.user.controller;

import com.smha.sms.user.model.entity.User;
import com.smha.sms.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;


@ControllerAdvice
@RequiredArgsConstructor
public class LoggedInUserController {
    private final UserRepository userRepository;

    @ModelAttribute("user")
    public User getCurrentUser(Principal principal) {
        if (principal == null) {
            return null;
        }

        return userRepository.findByUsername(principal.getName())
                .orElse(null);
    }
}