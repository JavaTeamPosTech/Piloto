package com.techchallenge.user_manager_api.services.impl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenServiceTest {

    private TokenService tokenService;
    private final String secret = "minhaChaveUltraSecretaComMaisDe32Bytes";
    private final long expiration = 3600000; // 1 hora

    @BeforeEach
    void setUp() {
        tokenService = new TokenService(secret, expiration);
    }

    @Test
    void deveGerarTokenComSucesso() {
        String login = "usuario";
        String token = tokenService.generateToken(login);

        assertNotNull(token);
        assertEquals(login, tokenService.extractUsername(token));
        assertFalse(tokenService.isTokenExpired(token));
    }

    @Test
    void deveValidarTokenComSucesso() {
        String login = "usuario";
        String token = tokenService.generateToken(login);

        boolean isValid = tokenService.validateToken(token, login);

        assertTrue(isValid);
    }

    @Test
    void deveRetornarTokenExpirado() throws InterruptedException {
        TokenService tokenExpirado = new TokenService(secret, 1);
        String token = tokenExpirado.generateToken("usuario");
        Thread.sleep(5);

        assertThrows(ExpiredJwtException.class, () -> {
            tokenExpirado.validateToken(token, "usuario");
        });
    }


    @Test
    void deveLancarExcecaoAoExtrairUsernameDeTokenInvalido() {
        Exception exception = assertThrows(JwtException.class, () -> {
            tokenService.extractUsername("tokenInvalido");
        });

        // A mensagem real da exceção é essa, então teste contra ela:
        assertTrue(exception.getMessage().contains("JWT strings must contain exactly 2 period characters"));
    }

    @Test
    void deveRetornarFalseSeUsernameNaoBater() {
        String token = tokenService.generateToken("usuario");

        boolean isValid = tokenService.validateToken(token, "outroUsuario");

        assertFalse(isValid);
    }
}
