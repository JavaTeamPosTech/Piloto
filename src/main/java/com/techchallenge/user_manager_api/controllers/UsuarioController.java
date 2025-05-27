package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.LoginResponseDTO;
import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.services.UsuarioService;
import com.techchallenge.user_manager_api.services.impl.AuthorizationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

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

    @Operation(
            summary = "Atualiza a senha do usuário",
            description = "Este endpoint permite que um usuário atualize sua senha, fornecendo a senha atual e a nova senha."
    )
    @PutMapping("/atualizarSenha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO) {
        usuarioService.atualizarSenha(atualizarSenhaRequestDTO);
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
