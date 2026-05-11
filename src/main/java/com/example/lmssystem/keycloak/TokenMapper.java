package com.example.lmssystem.keycloak;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenMapper {

    public TokenResponseDTO toDTO(Map<String, Object> keycloakResponse) {
        return TokenResponseDTO.builder()
                .accessToken((String) keycloakResponse.get("access_token"))
                .refreshToken((String) keycloakResponse.get("refresh_token"))
                .expiresIn(toLong(keycloakResponse.get("expires_in")))
                .refreshExpiresIn(toLong(keycloakResponse.get("refresh_expires_in")))
                .tokenType((String) keycloakResponse.get("token_type"))
                .build();
    }

    private Long toLong(Object value) {
        if (value instanceof Integer i) return i.longValue();
        if (value instanceof Long l) return l;
        return null;
    }
}
