package com.techchallenge.user_manager_api.controllers;

import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
