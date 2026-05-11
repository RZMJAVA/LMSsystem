package com.example.lmssystem.keycloak;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}