package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.response.LoginResponseDTO;
import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.services.UsuarioService;
import com.techchallenge.user_manager_api.services.impl.AuthorizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthorizationServiceImpl authorizationServiceImpl;

    public UsuarioController(UsuarioService usuarioService, AuthorizationServiceImpl authorizationServiceImpl) {
        this.usuarioService = usuarioService;
        this.authorizationServiceImpl = authorizationServiceImpl;
    }

    @Operation(summary = "Atualizar senha", description = "Permite o usuário logado atualizar sua senha.")
    @PutMapping("/atualizar-senha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO, Authentication authentication) {
        usuarioService.atualizarSenha(atualizarSenhaRequestDTO, authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Realiza o login de um usuário",
            description = "Este endpoint permite que um usuário faça login no sistema, retornando um token JWT se as credenciais forem válidas."
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authorizationServiceImpl.login(loginRequestDTO));
    }
}
