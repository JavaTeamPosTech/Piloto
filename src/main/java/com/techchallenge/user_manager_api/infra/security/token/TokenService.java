package com.techchallenge.user_manager_api.infra.security.token;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String generateToken(String login);
    Claims extractClaims(String token);
    String extractUsername(String token);
    boolean isTokenExpired(String token);
    boolean validateToken(String token, String username);
    boolean isTokenValid(String token, UserDetails userDetails);
}
