//package com.techchallenge.user_manager_api.application.services.impl;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.Date;
//
//@Service
//public class TokenService {
//
//    private final String secret;
//    private final long prazoExpiracao;
//
//    public TokenService(@Value("${jwt.secret}") String secret,
//                        @Value("${jwt.prazoExpiracao}") long prazoExpiracao) {
//        this.secret = secret;
//        this.prazoExpiracao = prazoExpiracao;
//    }
//
//    private Key getSigningKey() {
//        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateToken(String login) {
//        try {
//            return Jwts.builder()
//                    .setIssuer("user-manager-api")
//                    .setSubject(login)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(System.currentTimeMillis() + prazoExpiracao))
//                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                    .compact();
//
//        } catch (Exception exception) {
//            throw new RuntimeException("JWT generation failed", exception);
//        }
//    }
//
//    public Claims extractClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String extractUsername(String token) {
//        Claims claims = extractClaims(token);
//        if (claims == null) throw new JwtException("Invalid or Expired Token");
//        return claims.getSubject();
//    }
//
//    public boolean isTokenExpired(String token) {
//        Claims claims = extractClaims(token);
//        if (claims == null || claims.getExpiration() == null) {
//            throw new JwtException("Invalid or Expired Token");
//        }
//        return claims.getExpiration().before(new Date());
//    }
//
//    public boolean validateToken(String token, String username) {
//        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        String username = extractUsername(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }
//
//    private Instant genExpirationDate() {
//        return LocalDateTime.now().plusSeconds(3600).toInstant(ZoneOffset.of("-03:00"));
//    }
//}
