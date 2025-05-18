package com.techchallenge.user_manager_api.services;

import com.techchallenge.user_manager_api.dto.AtualizarSenhaRequestDTO;
import com.techchallenge.user_manager_api.dto.ClienteRequestDTO;
import com.techchallenge.user_manager_api.dto.ClienteResponseDTO;
import com.techchallenge.user_manager_api.dto.LoginRequestDTO;
import jakarta.validation.Valid;


public interface ClienteService {
    void cadastrarCliente(ClienteRequestDTO clienteDTO);

    ClienteResponseDTO buscarCliente(Long id);

    void fazerLogin(@Valid LoginRequestDTO loginRequestDTO);

    void atualizarSenha(Long id, @Valid AtualizarSenhaRequestDTO atualizarSenhaRequestDTO);
}
