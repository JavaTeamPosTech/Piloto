package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;


public interface ClienteService {
    void cadastrarCliente(ClienteRequestDTO clienteDTO);

    Boolean existeClientePorEmail(String email);

}
