package com.techchallenge.user_manager_api.services.impl;

import com.techchallenge.user_manager_api.naousar.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.naousar.entities.Usuario;
import com.techchallenge.user_manager_api.naousar.services.UsuarioService;
import com.techchallenge.user_manager_api.naousar.services.impl.AuthorizationServiceImpl;
import com.techchallenge.user_manager_api.naousar.services.impl.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Test
    void login_SuccessfulAuthentication() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("validLogin", "validPassword");
        Usuario usuario = new Usuario();
        usuario.setLogin("validLogin");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(usuarioService.existsByLogin(loginRequestDTO.login())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(usuario.getLogin())).thenReturn("generatedToken");

        LoginResponseDTO response = authorizationService.login(loginRequestDTO);

        assertNotNull(response);
        assertEquals("generatedToken", response.token());
    }

    @Test
    void login_UserNotFound() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("invalidLogin", "password");

        when(usuarioService.existsByLogin(loginRequestDTO.login())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> authorizationService.login(loginRequestDTO));
    }

    @Test
    void login_InvalidPassword() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("validLogin", "invalidPassword");

        when(usuarioService.existsByLogin(loginRequestDTO.login())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(UnauthorizedException.class, () -> authorizationService.login(loginRequestDTO));
    }
}