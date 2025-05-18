package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.ClienteResponseDTO;


public interface ClienteService {
    void cadastrarCliente(ClienteRequestDTO clienteDTO);

    ClienteResponseDTO buscarCliente(Long id);
}
