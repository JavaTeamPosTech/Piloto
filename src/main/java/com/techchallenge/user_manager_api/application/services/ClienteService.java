package com.techchallenge.user_manager_api.application.services;

import com.techchallenge.user_manager_api.naousar.dto.requests.AtualizarClienteRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.requests.ClienteRequestDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.CadastroResponseDTO;
import com.techchallenge.user_manager_api.naousar.dto.response.ClienteResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ClienteService {
    CadastroResponseDTO cadastrarCliente(ClienteRequestDTO clienteDTO);
    ClienteResponseDTO buscarCliente(UUID id);
    List<ClienteResponseDTO> buscarClientes();
    ClienteResponseDTO editarCliente(UUID id, AtualizarClienteRequestDTO clienteRequestDTO);
    void deletarCliente(UUID id);
}
