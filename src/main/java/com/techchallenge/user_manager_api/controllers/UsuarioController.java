package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.AtualizarUsuarioRequestDTO;
import com.techchallenge.user_manager_api.dto.UsuarioRequestDTO;
import com.techchallenge.user_manager_api.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Controller do Usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuario")
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid UsuarioRequestDTO usuario) {
        usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuario")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        usuarioService.removerUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar dados cadastrais do usuario, exceto login e senha")
    public ResponseEntity<Void> alterarUsuario(@RequestBody @Valid AtualizarUsuarioRequestDTO usuario, @PathVariable Long id) {
        usuarioService.alterarUsuario(usuario, id);
        return ResponseEntity.noContent().build();
    }
}
