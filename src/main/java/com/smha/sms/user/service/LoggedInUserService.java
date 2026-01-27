package com.smha.sms.user.service;

import com.smha.sms.user.model.entity.User;
import com.smha.sms.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggedInUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            return null;
        }

        String username = auth.getName();

        return userRepository.findByUserName(username)
                .orElse(null);
    }
}