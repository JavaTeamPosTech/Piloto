package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.AtualizarUsuarioRequestDTO;
import com.techchallenge.user_manager_api.dto.UsuarioDTO;
import com.techchallenge.user_manager_api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarUsuario(@RequestBody @Valid AtualizarUsuarioRequestDTO usuario, @PathVariable Long id) {
        usuarioService.alterarUsuario(usuario, id);
        return ResponseEntity.noContent().build();
    }
}
