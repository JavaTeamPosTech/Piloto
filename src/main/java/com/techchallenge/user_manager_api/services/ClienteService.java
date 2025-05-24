package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.response.ClienteResponseDTO;


public interface ClienteService {
    void cadastrarCliente(ClienteRequestDTO clienteDTO);

    ClienteResponseDTO buscarCliente(Long id);

}
