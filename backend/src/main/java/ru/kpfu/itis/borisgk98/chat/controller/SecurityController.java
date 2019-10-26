package ru.kpfu.itis.borisgk98.chat.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(StaticNames.SERVER_PREFIX)
@RequiredArgsConstructor
public class SecurityController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequest data, HttpServletResponse response) throws Throwable {
        try {
            String login = data.getLogin();
            String password = data.getPassword();
            User user = userService.findByLogin(login)
                    .orElseThrow(() -> new BadCredentialsException("Invalid username/password supplied"));
            if (!user.getPassHash().equals(password)) {
                throw new BadCredentialsException("Invalid username/password supplied");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            String token = jwtTokenProvider.createToken(
                    login,
//                    this.users.findByLogin(login)
//                            .orElseThrow(() -> new UsernameNotFoundException("Username " + login + "not found"))
//                            .getRoles()
//                            .stream().map(UserRole::getRole).collect(Collectors.toList())
                    Arrays.asList("none")
            );
            Map<Object, Object> model = new HashMap<>();
            model.put("login", login);
            model.put("token", token);
            response.addCookie(new Cookie(StaticNames.AUTH_HEADER, token));
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        catch (Throwable e) {
            throw e;
        }
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody AuthenticationRequest data, HttpServletResponse response) throws Throwable {
        try {
            User user = new User();
            if (!userService.existByLogin(data.getLogin())) {
                user.setLogin(data.getLogin());
                user.setPassHash(data.getPassword());
                userService.create(user);
            }
            else {
                throw new IllegalArgumentException();
            }
            String username = data.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(
                    username,
                    Arrays.asList("none")
            );
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            response.addCookie(new Cookie(StaticNames.AUTH_HEADER, token));
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        catch (Throwable e) {
            throw e;
        }
    }

    @PostMapping("logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        response.addCookie(new Cookie(StaticNames.AUTH_HEADER, ""));
        return ResponseEntity.ok("{}");
    }

}
