package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    public ProprietarioController(ProprietarioService proprietarioService) {
        this.proprietarioService = proprietarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarProprietario(@RequestBody ProprietarioRequestDTO proprietarioDTO) {
        proprietarioService.cadastrarProprietario(proprietarioDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioResponseDTO> buscarProprietarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proprietarioService.buscarProprietarioPorId(id));
    }




}
