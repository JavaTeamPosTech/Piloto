package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.UsuarioDTO;
import com.techchallenge.user_manager_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuario) {
        usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

}
