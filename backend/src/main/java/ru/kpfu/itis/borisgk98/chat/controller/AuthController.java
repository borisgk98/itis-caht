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
import ru.kpfu.itis.borisgk98.chat.model.entity.UserRole;
import ru.kpfu.itis.borisgk98.chat.repo.UserRepo;
import ru.kpfu.itis.borisgk98.chat.security.JwtTokenProvider;
import ru.kpfu.itis.borisgk98.chat.statical.StaticNames;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(StaticNames.SERVER_PREFIX + "auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepo users;

    @PostMapping()
    public ResponseEntity login(@RequestBody AuthenticationRequest data) throws Throwable {
        try {
            String username = data.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(
                    username,
                    this.users.findByLogin(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
                            .getRoles()
                            .stream().map(UserRole::getRole).collect(Collectors.toList())
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