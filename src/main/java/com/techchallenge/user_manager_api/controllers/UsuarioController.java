package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.LoginResponseDTO;
import com.techchallenge.user_manager_api.dto.requests.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.requests.LoginRequestDTO;
import com.techchallenge.user_manager_api.entities.Usuario;
import com.techchallenge.user_manager_api.services.UsuarioService;
import com.techchallenge.user_manager_api.services.impl.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @PostMapping("/login")
    public ResponseEntity<Void> fazerLogin(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        usuarioService.fazerLogin(loginRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizarSenha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO) {
        usuarioService.atualizarSenha(atualizarSenhaRequestDTO);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login2")
    public ResponseEntity<LoginResponseDTO> fazerLogin2(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        var user = new UsernamePasswordAuthenticationToken(loginRequestDTO.login(), loginRequestDTO.senha());
        var auth = authenticationManager.authenticate(user);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
