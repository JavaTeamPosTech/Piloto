package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.naousar.controllers.UsuarioController;
import com.techchallenge.user_manager_api.naousar.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.naousar.services.UsuarioService;
import com.techchallenge.user_manager_api.naousar.services.impl.AuthorizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    private AuthorizationServiceImpl authorizationServiceImpl;

    @BeforeEach
    void setUp() {
        usuarioService = Mockito.mock(UsuarioService.class);
        authorizationServiceImpl = Mockito.mock(AuthorizationServiceImpl.class);
        usuarioController = new UsuarioController(usuarioService, authorizationServiceImpl);
    }

    @Test
    void deveAtualizarSenhaComSucesso() {
        AtualizarSenhaRequestDTO dto = new AtualizarSenhaRequestDTO("senhaAtual", "novaSenha");
        Authentication auth = new UsernamePasswordAuthenticationToken("joaolima", null);

        doNothing().when(usuarioService).atualizarSenha(dto, auth);

        ResponseEntity<Void> response = usuarioController.atualizarSenha(dto, auth);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deveRetornar401AoAtualizarComSenhaAtualIncorreta() {
        AtualizarSenhaRequestDTO dto = new AtualizarSenhaRequestDTO("senhaErrada", "novaSenha");
        Authentication auth = new UsernamePasswordAuthenticationToken("joaolima", null);

        doThrow(new UnauthorizedException("Senha atual incorreta"))
                .when(usuarioService).atualizarSenha(dto, auth);

        assertThrows(UnauthorizedException.class, () -> usuarioController.atualizarSenha(dto, auth));
    }

    @Test
    void deveRealizarLoginComCredenciaisValidas() {
        LoginRequestDTO request = new LoginRequestDTO("usuario", "senha123");
        LoginResponseDTO responseDTO = new LoginResponseDTO("tokenJWT");

        when(authorizationServiceImpl.login(request)).thenReturn(responseDTO);

        ResponseEntity<LoginResponseDTO> response = usuarioController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void deveRetornar401AoRealizarLoginComCredenciaisInvalidas() {
        LoginRequestDTO request = new LoginRequestDTO("usuario", "senhaErrada");

        when(authorizationServiceImpl.login(request)).thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThrows(BadCredentialsException.class, () -> usuarioController.login(request));
    }
}
