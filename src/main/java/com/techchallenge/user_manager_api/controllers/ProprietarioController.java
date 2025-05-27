package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.requests.ProprietarioRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ProprietarioResponseDTO;
import com.techchallenge.user_manager_api.services.ProprietarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/proprietario")
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    public ProprietarioController(ProprietarioService proprietarioService) {
        this.proprietarioService = proprietarioService;
    }

    @PostMapping
    public ResponseEntity<ProprietarioResponseDTO> cadastrarProprietario(@RequestBody @Valid ProprietarioRequestDTO proprietarioDTO, UriComponentsBuilder uriBuilder) {
        ProprietarioResponseDTO proprietarioResponseDTO = proprietarioService.cadastrarProprietario(proprietarioDTO);
        URI uri = uriBuilder.path("/proprietario/{id}")
                .buildAndExpand(proprietarioResponseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(proprietarioResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioResponseDTO> buscarProprietarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proprietarioService.buscarProprietarioPorId(id));
    }




}
