package com.techchallenge.user_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.exceptions.UnauthorizedException;
import com.techchallenge.user_manager_api.services.UsuarioService;
import com.techchallenge.user_manager_api.services.impl.AuthorizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "joaolima123", roles = {"PROPRIETARIO"})
class UsuarioControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService usuarioService;
    @MockBean
    private AuthorizationServiceImpl authorizationServiceImpl;

    @Mock
    private Authentication authentication;

    @Test
    void deveAtualizarSenhaComSucesso() throws Exception {
        AtualizarSenhaRequestDTO requestDTO = new AtualizarSenhaRequestDTO("senhaAntiga123", "senhaNova456");
        Authentication authentication = new UsernamePasswordAuthenticationToken("usuario", null);

        String json = """
                {
                "senhaAtual": "senhaAntiga123",
                    "novaSenha": "senhaNova456"
                    }
                """;

        doNothing().when(usuarioService).atualizarSenha(requestDTO, authentication);

        var response = mvc.perform(
                put("/usuarios/atualizar-senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .principal(authentication)
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar401AoAtualizarComSenhaAtualIncorreta() throws Exception {
        AtualizarSenhaRequestDTO requestDTO = new AtualizarSenhaRequestDTO("senhaAntiga123", "senhaNova456");

        String json = """
                {
                "senhaAtual": "senhaAntiga123",
                    "novaSenha": "senhaNova456"
                    }
                """;

        doThrow(new UnauthorizedException("Senha atual incorreta"))
                .when(usuarioService).atualizarSenha(eq(requestDTO), any(Authentication.class));



        var response = mvc.perform(
                put("/usuarios/atualizar-senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(401, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtualizarSenhaComDadosInvalidos() throws Exception {
        var response = mvc.perform(
                put("/usuarios/atualizar-senha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "senhaAtual": "",
                                    "novaSenha": "senhaNova456"
                                }
                                """)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void deveRealizarLoginComCredenciaisValidas() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("usuario", "senha123");
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO("tokenJWT");
        when(authorizationServiceImpl.login(loginRequestDTO)).thenReturn(loginResponseDTO);

        String json = """
                {
                    "login": "usuario",
                    "senha": "senha123"
                }
                """;

        var response = mvc.perform(
                post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }


    @Test
    void deveRetornar401AoRealizarLoginComCredenciaisInvalidas() throws Exception {
        when(authorizationServiceImpl.login(any(LoginRequestDTO.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        String json = """
            {
                "login": "usuario",
                "senha": "senha123"
            }
            """;

        var response = mvc.perform(
                post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();

        assertEquals(401, response.getStatus());
    }

}