package ru.kpfu.itis.borisgk98.chat.model.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String login;
    private String password;
}
