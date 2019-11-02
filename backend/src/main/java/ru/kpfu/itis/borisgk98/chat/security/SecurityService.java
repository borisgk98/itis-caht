package ru.kpfu.itis.borisgk98.chat.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.borisgk98.chat.model.entity.User;
import ru.kpfu.itis.borisgk98.chat.service.UserService;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String login = authentication.getName();
        if (login != null) {
            return userService.findByLogin(login).orElse(null);
        }
        return null;
    }
}
