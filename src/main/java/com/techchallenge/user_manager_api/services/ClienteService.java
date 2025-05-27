package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;
import com.techchallenge.user_manager_api.entities.Cliente;

import java.util.UUID;


public interface ClienteService {
    Cliente cadastrarCliente(ClienteRequestDTO clienteDTO);

    ClienteResponseDTO buscarCliente(UUID id);

}
