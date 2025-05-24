package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
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


    @PostMapping("/login")
    public ResponseEntity<Void> fazerLogin(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        usuarioService.fazerLogin(loginRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizarSenha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO){
        usuarioService.atualizarSenha(atualizarSenhaRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
