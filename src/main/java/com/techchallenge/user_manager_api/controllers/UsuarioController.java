package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.LoginResponseDTO;
import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.services.UsuarioService;
import com.techchallenge.user_manager_api.services.impl.AuthorizationServiceImpl;
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

    @PutMapping("/atualizarSenha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO) {
        usuarioService.atualizarSenha(atualizarSenhaRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authorizationServiceImpl.login(loginRequestDTO));
    }
}
