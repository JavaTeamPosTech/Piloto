package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.ClienteResponseDTO;
import com.techchallenge.user_manager_api.dto.LoginRequestDTO;
import com.techchallenge.user_manager_api.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO clienteDTO){
        clienteService.cadastrarCliente(clienteDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.buscarCliente(id));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> fazerLogin(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        clienteService.fazerLogin(loginRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Long id, @RequestBody @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO){
        clienteService.atualizarSenha(id, atualizarSenhaRequestDTO);
        return ResponseEntity.noContent().build();
    }


}
