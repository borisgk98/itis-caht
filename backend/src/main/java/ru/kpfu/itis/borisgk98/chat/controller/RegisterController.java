package ru.kpfu.itis.borisgk98.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.borisgk98.chat.model.dto.AuthenticationRequest;
import ru.kpfu.itis.borisgk98.chat.model.entity.User;
import ru.kpfu.itis.borisgk98.chat.model.entity.UserRole;
import ru.kpfu.itis.borisgk98.chat.repo.UserRepo;
import ru.kpfu.itis.borisgk98.chat.security.JwtTokenProvider;
import ru.kpfu.itis.borisgk98.chat.service.UserService;
import ru.kpfu.itis.borisgk98.chat.statical.StaticNames;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(StaticNames.SERVER_PREFIX + "register")
public class RegisterController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepo users;
    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity register(@RequestBody AuthenticationRequest data) throws Throwable {
        try {
            User user = new User();
            if (!userService.existByLogin(data.getLogin())) {
                user.setLogin(data.getLogin());
                user.setPassHash(data.getPassword());
                userService.create(user);
            }
            String username = data.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(
                    username,
                    Collections.EMPTY_LIST
            );
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        catch (Throwable e) {
            throw e;
        }
    }
}
